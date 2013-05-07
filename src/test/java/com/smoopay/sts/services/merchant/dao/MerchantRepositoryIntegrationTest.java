package com.smoopay.sts.services.merchant.dao;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.entity.common.account.MerchantAccount;
import com.smoopay.sts.entity.common.account.QMerchantAccount;
import com.smoopay.sts.entity.common.address.MerchantAddress;
import com.smoopay.sts.entity.common.address.QMerchantAddress;
import com.smoopay.sts.entity.merchant.Merchant;
import com.smoopay.sts.entity.merchant.QMerchant;
import com.smoopay.sts.entity.payments.Payment;
import com.smoopay.sts.entity.payments.QPayment;
import com.smoopay.sts.repository.merchant.MerchantAccountRepository;
import com.smoopay.sts.repository.merchant.MerchantRepository;
import com.smoopay.sts.repository.payment.PaymentRepository;
import com.smoopay.sts.utils.CollectionUtils;

/**
 * http://www.hostettler.net/blog/2012/03/22/one-to-one-relations-in-jpa-2-dot-0
 * 
 * @author Adam Dec
 * 
 */
public class MerchantRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private CollectionUtils collectionUtils;

	@Autowired
	private MerchantRepository merchantRepository;

	@Autowired
	private MerchantAccountRepository merchantAccountRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Test
	public void shouldReturnAllMerchants() {
		// given
		// when
		List<Merchant> merchants = merchantRepository.findAll();

		// than
		assertThat(merchants, is(notNullValue()));
		assertThat(merchants, hasSize(2));
	}

	@Test
	public void shouldgetMerchantbyName() {
		// given
		final String name = "Orlen";

		// when
		Merchant merchant = merchantRepository.findByName(name);

		// than
		assertThat(merchant, is(notNullValue()));
		assertThat(name, is(merchant.getName()));
	}

	@Test
	public void shouldDisableTescoMerchant() {
		// given
		final String name = "Tesco";

		// when
		Merchant merchant = merchantRepository.findByName(name);
		merchant.setEnabled(false);
		merchantRepository.save(merchant);

		// than
		assertThat(merchant, is(notNullValue()));
		assertThat(false, is(merchantRepository.findByName(name).isEnabled()));
	}

	@Test
	public void shouldGetMerchantBankAccountUsingQueryDSL() {
		// given
		QMerchant m = QMerchant.merchant;
		BooleanExpression nameStartsWithT = m.name.startsWith("T");

		// when
		Merchant merchant = merchantRepository.findOne(nameStartsWithT);
		MerchantAccount merchantAccount = merchant.getMerchantAccount();

		// than
		assertThat(merchantAccount, is(notNullValue()));
		assertThat("PLN", is(merchantAccount.getCurrency().getValue()));
	}

	@Test
	public void shouldRemoveMerchantWithCascade() {
		// given
		QMerchant m = QMerchant.merchant;
		BooleanExpression nameStartsWithT = m.name.startsWith("O");
		BooleanExpression enabled = m.enabled.eq(true);

		// when
		Merchant merchant = merchantRepository.findOne(nameStartsWithT.and(enabled));
		merchantRepository.delete(merchant);
		merchant = merchantRepository.findOne(nameStartsWithT.and(enabled));
		MerchantAccount merchantAccount = merchantAccountRepository.findByVirtualAccNo("M01");

		// than
		Assert.assertNull(merchant);
		Assert.assertNull(merchantAccount);
	}

	@Test
	public void shouldGetMerchantAccountUsingJoin() {
		// given
		QMerchant merchant = QMerchant.merchant;
		BooleanExpression nameStartsWithT = merchant.name.startsWith("O");
		QMerchantAccount merchantAccount = QMerchantAccount.merchantAccount;
		JPAQuery query = null;

		// when
		query = new JPAQuery(em);
		MerchantAccount merchantAcc = query.from(merchantAccount).join(merchantAccount.merchant, merchant).fetch().where(nameStartsWithT).singleResult(merchantAccount);

		// than
		assertThat(merchantAcc, is(notNullValue()));
	}

	@Test
	public void shouldGetMerchantAccountUsingJoinOneQuery() {
		// given
		Session session = em.unwrap(Session.class);

		// when
		List<?> resultList = session.createCriteria(Merchant.class).setFetchMode("merchantAccount", FetchMode.SELECT).setFetchMode("merchantAddress", FetchMode.SELECT)
				.setFetchMode("payments", FetchMode.SELECT).add(Restrictions.like("name", "Orlen")).list();

		// than
		assertThat(resultList.size(), is(1));
	}

	@Test
	public void shouldGetMerchantAddress() {
		// given
		QMerchant merchant = QMerchant.merchant;
		BooleanExpression nameStartsWithT = merchant.name.startsWith("O");
		QMerchantAddress merchantAddress = QMerchantAddress.merchantAddress;
		JPAQuery query = null;

		// when
		query = new JPAQuery(em);
		MerchantAddress merchantAddr = query.from(merchantAddress).join(merchantAddress.merchant, merchant).fetch().where(nameStartsWithT).singleResult(merchantAddress);

		// than
		assertThat(merchantAddr, is(notNullValue()));
	}

	@Test
	public void shouldGetMerchantPayments() {
		// given
		QMerchant merchant = QMerchant.merchant;
		BooleanExpression nameStartsWithT = merchant.name.startsWith("O");
		QPayment payment = QPayment.payment;
		JPAQuery query = null;

		// when
		query = new JPAQuery(em);
		List<Payment> list = query.from(payment).join(payment.merchant, merchant).fetch().where(nameStartsWithT).list(payment);

		// than
		assertThat(list, is(notNullValue()));
		assertThat(list.size(), is(2));
	}

	@Test
	public void shouldDeleteMerchantPaymentsUsingCascade() {
		// given
		QMerchant m = QMerchant.merchant;
		BooleanExpression nameStartsWithT = m.name.startsWith("O");
		BooleanExpression enabled = m.enabled.eq(true);
		QPayment p = QPayment.payment;
		BooleanExpression id = p.merchant.id.eq(1L);

		// when
		Merchant merchant = merchantRepository.findOne(nameStartsWithT.and(enabled));
		merchantRepository.delete(merchant);
		Iterable<Payment> payments = paymentRepository.findAll(id);

		// than
		Assert.assertNull(merchantRepository.findOne(nameStartsWithT.and(enabled)));
		assertThat(collectionUtils.copyIterator(payments.iterator()).size(), is(0));
	}

	@Test
	public void shouldDeleteMerchantPaymentsUsingOrphan() {
		// given
		QMerchant m = QMerchant.merchant;
		BooleanExpression nameStartsWithT = m.name.startsWith("O");
		BooleanExpression enabled = m.enabled.eq(true);
		QPayment p = QPayment.payment;
		BooleanExpression id = p.merchant.id.eq(1L);

		// when
		Merchant merchant = merchantRepository.findOne(nameStartsWithT.and(enabled));
		merchant.getPayments().clear();
		Iterable<Payment> payments = paymentRepository.findAll(id);

		// than
		assertThat(payments.iterator().hasNext(), is(false));
	}
}