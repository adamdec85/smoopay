package com.smoopay.sts.repository.bank;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.account.BankAccount;

/**
 * {@link Repository} to access {@link BankAccount} instances.
 * 
 * @author Adam Dec
 */
public interface BankAccountRepository extends PagingAndSortingRepository<BankAccount, Long>, QueryDslPredicateExecutor<BankAccount> {
}