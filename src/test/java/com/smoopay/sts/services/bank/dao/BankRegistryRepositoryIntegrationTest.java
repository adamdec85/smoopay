package com.smoopay.sts.services.bank.dao;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.ejb.EntityManagerFactoryImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryInfo;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.entity.bank.BankRegistry;
import com.smoopay.sts.repository.bank.BankRegistryRepository;
import com.smoopay.sts.utils.AccountPLUtils;

/**
 * Integration tests for {@link BankRegistryRepository}.
 * 
 * http://anirbanchowdhury.wordpress.com/2012/07/23/hibernate-second-level-cache
 * -ehcache/
 * 
 * http://docs.oracle.com/javaee/6/tutorial/doc/gkjjj.html
 * 
 * @author Adam Dec
 */
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class BankRegistryRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private BankRegistryRepository bankRegistryRepository;

	@Autowired
	private AccountPLUtils accountPLUtils;

	@Test
	public void shouldGetBankNameByBankIdWithNoCacheing() {
		// given
		final Long bankId = 1240L;

		// when
		BankRegistry bankRegistry = bankRegistryRepository.findByBankId(bankId);
		bankRegistryRepository.findByBankId(bankId);
		bankRegistryRepository.findByBankId(bankId);

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO"));
		getCacheL2HitCount();
	}

	@Test
	public void shouldGetBankNameByBankIdWithNativeQueryCacheing() {
		// given
		final Long bankId = 1240L;

		// when
		String hql = "SELECT b FROM BankRegistry b WHERE b.bankId = :bankId";
		Query query = em.createQuery(hql);
		query.setHint("org.hibernate.cacheable", true);
		query.setParameter("bankId", bankId);

		BankRegistry bankRegistry = (BankRegistry) query.getSingleResult();

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO"));
		getCacheL2HitCount();
	}

	@Test
	public void shouldGetBankNameByBankIdWithNamedQueryCacheing() {
		// given
		final Long bankId = 1240L;

		// when
		Query query = em.createNamedQuery("byBankId");
		query.setHint("org.hibernate.cacheable", true);
		query.setParameter("bankId", bankId);

		BankRegistry bankRegistry = (BankRegistry) query.getSingleResult();

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO"));
		getCacheL2HitCount();
	}

	/**
	 * Uses javax.persistence.sharedCache.mode
	 */
	@Test
	public void shouldGetBankNameByBankIdWithCriteriaAPICacheing() {
		// given
		final Long bankId = 1240L;

		// when
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BankRegistry> cq = cb.createQuery(BankRegistry.class);
		Root<BankRegistry> root = cq.from(BankRegistry.class);
		cq.select(root);
		cq.where(cb.equal(root.get("bankId"), bankId));
		TypedQuery<BankRegistry> createQuery = em.createQuery(cq).setHint("org.hibernate.cacheable", true);
		BankRegistry bankRegistry = createQuery.getSingleResult();

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO"));
		getCacheL2HitCount();
	}

	@Test
	public void shouldGetBankNameByAccountNRB() {
		// given
		final long[] bankId = accountPLUtils.getBankIdFromAccount("11 10202498 000086020263XXXX");

		// when
		BankRegistry bankRegistry = bankRegistryRepository.findByBankId(bankId[0]);
		if (bankRegistry == null) {
			bankRegistry = bankRegistryRepository.findByBankId(bankId[1]);
		}

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO BP"));
	}

	@Test
	public void shouldGetBankNameByAccountNRBSplitted() {
		// given
		final long[] bankId = accountPLUtils.getBankIdFromAccount("11 1020 2498 000086020263XXXX");

		// when
		BankRegistry bankRegistry = bankRegistryRepository.findByBankId(bankId[0]);
		if (bankRegistry == null) {
			bankRegistry = bankRegistryRepository.findByBankId(bankId[1]);
		}

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO BP"));
	}

	@Test
	public void shouldGetBankNameByAccountIBAN() {
		// given
		final long[] bankId = accountPLUtils.getBankIdFromAccount("PL 11 10202498 000086020263XXXX");

		// when
		BankRegistry bankRegistry = bankRegistryRepository.findByBankId(bankId[0]);
		if (bankRegistry == null) {
			bankRegistry = bankRegistryRepository.findByBankId(bankId[1]);
		}

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("PKO BP"));
	}

	@Test
	public void shouldGetBankNameByAccountIBANMultiple() {
		// given
		final long[] bankId = accountPLUtils.getBankIdFromAccount("PL 11 11402004 000086020263XXXX");

		// when
		BankRegistry bankRegistry = bankRegistryRepository.findByBankId(bankId[0]);
		if (bankRegistry == null) {
			bankRegistry = bankRegistryRepository.findByBankId(bankId[1]);
		}

		// than
		assertThat(bankRegistry, is(notNullValue()));
		assertThat(bankRegistry.getBankName(), is("mBank"));
	}

	private long getCacheL2HitCount() {
		EntityManagerFactoryInfo entityManagerFactoryInfo = (EntityManagerFactoryInfo) em.getEntityManagerFactory();
		EntityManagerFactory emf = entityManagerFactoryInfo.getNativeEntityManagerFactory();
		EntityManagerFactoryImpl emfImp = (EntityManagerFactoryImpl) emf;
		System.out.println(emfImp.getSessionFactory().getStatistics().getSecondLevelCacheStatistics("com.smoopay.sts.entity.bank.BankRegistry"));
		System.out.println(emfImp.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()[0]);
		System.out.println(emfImp.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
		return emfImp.getSessionFactory().getStatistics().getSecondLevelCacheHitCount();
	}
}