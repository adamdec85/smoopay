package com.smoopay.sts.services.payment.dao;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.entity.common.PaymentStatus;
import com.smoopay.sts.entity.merchant.Merchant;
import com.smoopay.sts.entity.payments.Payment;
import com.smoopay.sts.entity.payments.QPayment;
import com.smoopay.sts.repository.merchant.MerchantRepository;
import com.smoopay.sts.repository.payment.PaymentRepository;
import com.smoopay.sts.utils.CollectionUtils;

/**
 * @author Adam Dec
 */
public class PaymentRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private CollectionUtils collectionUtils;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Test
	public void shouldCreateNewPaymentForGivenMerchant() {
		// given
		Merchant merchant = merchantRepository.findByName("Tesco");
		Payment payment = new Payment(3L, new BigDecimal("300.0"), CurrencyEnum.PLN);
		payment.setMerchant(merchant);

		// when
		paymentRepository.save(payment);
		List<Payment> payments = paymentRepository.findByPosId(3L);

		// than
		assertThat(payments, is(notNullValue()));
		assertThat(payments, hasSize(1));
	}

	@Test
	public void shouldChangePaymentToCanceledStatus() {
		// given
		final String name = "Tesco";
		Merchant merchant = merchantRepository.findByName(name);

		// when
		Set<Payment> payments = merchant.getPayments();
		((Payment) payments.toArray()[0]).setPaymentStatus(new PaymentStatus(PaymentStatusEnum.CANCELED));
		merchantRepository.save(merchant);

		List<Payment> newPayments = paymentRepository.findByPaymentStatus(new PaymentStatus(PaymentStatusEnum.CANCELED));

		// than
		assertThat(newPayments.get(0).getPaymentStatus().getValue(), is("CANCELED"));
	}

	@Test
	public void shouldReturnAllPaymanets() {
		// given
		// when
		List<Payment> payments = paymentRepository.findAll();

		// than
		assertThat(payments, is(notNullValue()));
		assertThat(payments, hasSize(4));
	}

	@Test
	public void shouldGetPaymentsByMerchantId() {
		// given
		QPayment payment = QPayment.payment;
		BooleanExpression id = payment.merchant.id.eq(1L);

		// when
		Iterable<Payment> payments = paymentRepository.findAll(id);

		// than
		assertThat(collectionUtils.copyIterator(payments.iterator()).size(), is(2));
	}

	@Test
	public void shouldGetPaymentsByPaymentStatus() {
		// given
		final PaymentStatus paymentStatus = new PaymentStatus(PaymentStatusEnum.PENDING);

		// when
		List<Payment> payments = paymentRepository.findByPaymentStatus(paymentStatus);

		// than
		assertThat(payments.get(0).getAmount().doubleValue(), is(100.0D));
	}

	@Test
	public void shouldGetPaymentsByPosId() {
		// given
		final long posId = 1;

		// when
		List<Payment> payments = paymentRepository.findByPosId(posId);

		// than
		assertThat(payments.get(0).getPaymentStatus().getValue(), is("PENDING"));
	}

	@Test
	public void shouldGetPendingPaymentByMerchantIdAndPosIdUsingJPAQuery() {
		// given
		final long merchantId = 1;
		final long posId = 1;
		QPayment payment = QPayment.payment;
		BooleanExpression posPredicate = payment.posId.eq(posId);
		BooleanExpression statusPredicate = payment.paymentStatus.value.eq("PENDING");
		BooleanExpression merchantPredicate = payment.merchant.id.eq(merchantId);
		JPAQuery query = new JPAQuery(em);

		// when
		Payment p = query.from(payment).where(posPredicate, merchantPredicate, statusPredicate).singleResult(payment);

		// than
		assertThat(p.getPaymentStatus().getValue(), is("PENDING"));
	}

	@Test
	public void shouldGetPendingPaymentByMerchantIdAndPosIdUsingRepository() {
		// given
		final long merchantId = 1;
		final long posId = 1;
		QPayment payment = QPayment.payment;
		BooleanExpression posPredicate = payment.posId.eq(posId);
		BooleanExpression statusPredicate = payment.paymentStatus.value.eq("PENDING");
		BooleanExpression merchantPredicate = payment.merchant.id.eq(merchantId);

		// when
		Payment p = paymentRepository.findOne(posPredicate.and(merchantPredicate).and(statusPredicate));

		// than
		assertThat(p.getPaymentStatus().getValue(), is("PENDING"));
	}

	@Test
	public void shouldGetPaymentsSummedAmounntByMerchantId() {
		// given
		QPayment payment = QPayment.payment;
		BooleanExpression id = payment.merchant.id.eq(1L);
		JPAQuery query = null;

		// when
		query = new JPAQuery(em);
		BigDecimal sum = query.from(payment).where(id).groupBy(payment.amount).singleResult(payment.amount.sum());

		// than
		assertThat(sum.doubleValue(), is(200.0D));
	}
}