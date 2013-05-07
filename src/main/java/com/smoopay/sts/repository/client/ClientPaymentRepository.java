package com.smoopay.sts.repository.client;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.payments.ClientPayment;

/**
 * {@link Repository} to access {@link ClientPayment} instances.
 * 
 * @author Adam Dec
 */
public interface ClientPaymentRepository extends CrudRepository<ClientPayment, Long>, QueryDslPredicateExecutor<ClientPayment> {
}