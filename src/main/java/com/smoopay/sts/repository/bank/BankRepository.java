package com.smoopay.sts.repository.bank;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.bank.Bank;

/**
 * {@link Repository} to access {@link Bank} instances.
 * 
 * @author Adam Dec
 */
public interface BankRepository extends PagingAndSortingRepository<Bank, Long>, QueryDslPredicateExecutor<Bank> {
}