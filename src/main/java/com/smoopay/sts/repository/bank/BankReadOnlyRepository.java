package com.smoopay.sts.repository.bank;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.bank.Bank;
import com.smoopay.sts.repository.readonly.ReadOnlyRepository;

/**
 * {@link Repository} to read {@link Bank} instances.
 * 
 * @author Adam Dec
 */
public interface BankReadOnlyRepository extends ReadOnlyRepository<Bank>, QueryDslPredicateExecutor<Bank> {

	/**
	 * Returns the Bank.
	 * 
	 * @param name
	 *            the String to search for.
	 * @return Bank
	 */
	Bank findByName(String name);
}