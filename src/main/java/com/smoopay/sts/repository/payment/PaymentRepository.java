package com.smoopay.sts.repository.payment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import com.smoopay.sts.entity.common.PaymentStatus;
import com.smoopay.sts.entity.payments.Payment;

/**
 * {@link Repository} to access {@link PaymentRepository} instances.
 * 
 * @author Adam Dec
 */
public interface PaymentRepository extends CrudRepository<Payment, Long>, QueryDslPredicateExecutor<Payment> {

	/**
	 * Returns all Payments.
	 * 
	 * @return List of Payment
	 */
	List<Payment> findAll();

	/**
	 * Returns the Payment.
	 * 
	 * @param posId
	 *            the Long to search for.
	 * @return List of Payment
	 */
	List<Payment> findByPosId(Long posId);

	/**
	 * Returns the Payment.
	 * 
	 * @param paymentStatus
	 *            the PaymentStatus to search for.
	 * @return List of Payment
	 */
	List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);

	/**
	 * Returns the Payment.
	 * 
	 * @param amount
	 *            the BigDecimal to search for.
	 * @return List of Payment
	 */
	List<Payment> findByAmount(BigDecimal amount);

	/**
	 * Returns the Payment.
	 * 
	 * @param creationTime
	 *            the Date to search for.
	 * @return List of Payment
	 */
	List<Payment> findByCreationTime(Date creationTime);
}