package com.smoopay.sts.repository.bank;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.bank.BankRegistry;

/**
 * {@link Repository} to access {@link BankRegistry} instances.
 * 
 * @author Adam Dec
 */
public interface BankRegistryRepository extends PagingAndSortingRepository<BankRegistry, Long>, QueryDslPredicateExecutor<BankRegistry> {

	/**
	 * Returns the bank name.
	 * 
	 * @param bankId
	 *            the {@link Long} to search for.
	 * @return BankRegistry
	 */
	BankRegistry findByBankId(Long bankId);
}