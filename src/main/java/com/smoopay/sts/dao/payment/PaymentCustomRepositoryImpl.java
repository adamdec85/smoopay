package com.smoopay.sts.dao.payment;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.entity.bank.BankRegistry;
import com.smoopay.sts.entity.common.PaymentStatus;
import com.smoopay.sts.entity.common.account.BankAccount;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.QBankAccount;
import com.smoopay.sts.entity.merchant.Merchant;
import com.smoopay.sts.entity.payments.Payment;
import com.smoopay.sts.entity.payments.QPayment;
import com.smoopay.sts.repository.bank.BankAccountRepository;
import com.smoopay.sts.repository.client.ClientAccountRepository;
import com.smoopay.sts.repository.merchant.MerchantRepository;
import com.smoopay.sts.repository.payment.PaymentRepository;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.services.payment.client.response.AckPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.request.AmendPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.CancelPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.response.AmendPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.CancelPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.NewPaymentResponse_1_0;
import com.smoopay.sts.utils.AccountPLUtils;

@Component
@Transactional
public class PaymentCustomRepositoryImpl implements PaymentCustomRepository {

	@PersistenceContext
	protected EntityManager em;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Autowired
	private AccountPLUtils accountPLUtils;

	@Override
	public NewPaymentResponse_1_0 createOrAddNewPayment(NewPaymentRequest_1_0 request) {
		Merchant merchant = merchantRepository.findOne(request.getMerchantId());
		Payment newPayment = new Payment(request.getPosId(), request.getAmount(), request.getCurrency());
		newPayment.setMerchant(merchant);
		paymentRepository.save(newPayment);

		// TODO: Make builder pattern or factory for this
		// TODO: Should this be included in the scope of this transaction
		return new NewPaymentResponse_1_0(newPayment.getId(), request.getAmount(), newPayment.getCurrency().getEnumValue());
	}

	@Transactional(readOnly = true)
	@Override
	public NewPaymentResponse_1_0 checkPaymentStatus(Long paymentId) {
		Payment payment = paymentRepository.findOne(paymentId);
		return new NewPaymentResponse_1_0(payment.getId(), payment.getAmount(), payment.getCurrency().getEnumValue(), payment.getPaymentStatus().getEnumValue());
	}

	@Override
	public CancelPaymentResponse_1_0 cancelPayment(CancelPaymentRequest_1_0 request) {
		Payment payment = paymentRepository.findOne(request.getPaymentId());
		payment.setPaymentStatus(new PaymentStatus(PaymentStatusEnum.CANCELED));
		payment.setModifyTime(new Date());
		paymentRepository.save(payment);
		return new CancelPaymentResponse_1_0(payment.getId(), payment.getAmount(), payment.getCurrency().getEnumValue(), payment.getPaymentStatus().getEnumValue());
	}

	@Override
	public AmendPaymentResponse_1_0 amendPayment(AmendPaymentRequest_1_0 request) {
		// Amend old one
		Payment payment = paymentRepository.findOne(request.getPaymentId());
		payment.setPaymentStatus(new PaymentStatus(PaymentStatusEnum.AMENDED));
		payment.setModifyTime(new Date());
		paymentRepository.save(payment);

		// Create a new one with PENDING status
		Merchant merchant = merchantRepository.findOne(request.getMerchantId());
		Payment newPayment = new Payment(request.getPosId(), request.getAmount(), request.getCurrency());
		newPayment.setMerchant(merchant);
		paymentRepository.save(newPayment);
		return new AmendPaymentResponse_1_0(newPayment.getId(), request.getAmount(), newPayment.getCurrency().getEnumValue());
	}

	@Override
	public AckPaymentResponse_1_0 ackPendingPaymentForGivenPos(AckPaymentRequest_1_0 request) {
		final Payment merchantPaymentToAck = findPendingPaymentByMerchantIdAndPosId(request.getMerchantId(), request.getPosId());
		final ClientAccount clientAccount = clientCustomRepository.getClientAccountByVirtualAccNo(request.getClientId(), request.getVirtualAccNo());

		// Change payment status to FILLED
		merchantPaymentToAck.setPaymentStatus(new PaymentStatus(PaymentStatusEnum.FILLED));
		// Change payment modify date
		merchantPaymentToAck.setModifyTime(new Date());
		// Save updated payment
		paymentRepository.save(merchantPaymentToAck);

		// Search for a bank using account NRB number
		final BankRegistry bankRegistry = getBankRegistryForGivenBankId(clientAccount.getClientAccountData().getCashAccNRB());

		QBankAccount bankAcc = QBankAccount.bankAccount;
		BooleanExpression name = bankAcc.bankAccountData.bankName.eq(bankRegistry.getBankName());
		final BankAccount bankAccount = bankAccountRepository.findOne(name);

		// Balance client and bank account
		clientAccount.getClientFinancialBalance().setPrimaryAccountBalance(clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().subtract(request.getAmount()));

		// This will be equal to 0 after we sent all the payments to Elixir
		// session for given bank
		bankAccount.getBankFinancialBalance().setLockedBalance(bankAccount.getBankFinancialBalance().getLockedBalance().add(request.getAmount()));

		bankAccountRepository.save(bankAccount);
		clientAccountRepository.save(clientAccount);

		return new AckPaymentResponse_1_0(merchantPaymentToAck.getId(), merchantPaymentToAck.getPaymentStatus().getEnumValue());
	}

	@Transactional(readOnly = true)
	@Override
	public Payment findPendingPaymentByMerchantIdAndPosId(Long merchantId, Long posId) {
		BooleanExpression posIdPredicate = QPayment.payment.posId.eq(posId);
		BooleanExpression statusPredicate = QPayment.payment.paymentStatus.value.eq(PaymentStatusEnum.PENDING.toString());
		BooleanExpression merchantIdPredicate = QPayment.payment.merchant.id.eq(merchantId);
		return paymentRepository.findOne(posIdPredicate.and(merchantIdPredicate).and(statusPredicate));
	}

	private BankRegistry getBankRegistryForGivenBankId(String account) {
		long[] bankIds = accountPLUtils.getBankIdFromAccount(account);
		Query query = em.createNamedQuery("byBankId");
		query.setHint("org.hibernate.cacheable", true);
		query.setParameter("bankId", bankIds[0]);
		BankRegistry bankRegistry = (BankRegistry) query.getSingleResult();
		if (bankRegistry == null) {
			query.setParameter("bankId", bankIds[1]);
			return (BankRegistry) query.getSingleResult();
		}
		return bankRegistry;
	}
}