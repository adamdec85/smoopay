package com.smoopay.sts.services.payment.dao;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.AbstractIntegrationTest;
import com.smoopay.sts.entity.payments.ClientPayment;
import com.smoopay.sts.entity.payments.QClientPayment;
import com.smoopay.sts.repository.client.ClientPaymentRepository;
import com.smoopay.sts.utils.CollectionUtils;

/**
 * @author Adam Dec
 * 
 */
public class PaymentClientRepositoryIntegrationTest extends AbstractIntegrationTest {

	@Autowired
	private ClientPaymentRepository clientPaymentRepository;

	@Autowired
	private CollectionUtils collectionUtils;
	
	@Test
	public void shouldGetClientsPaymentsInAscOrder() {
		// given
		DateTime now = new DateTime(new Date().getTime());
		now = now.minusMinutes(1);
		QClientPayment payment = QClientPayment.clientPayment;
		Long clientId = 1L;
		BooleanExpression clientIdPredicate = payment.client.id.eq(clientId);
		BooleanExpression creationTimePredicate = payment.creationTime.after(now.toDate());

		// when
		Iterable<ClientPayment> clientPayments = clientPaymentRepository.findAll(clientIdPredicate.and(creationTimePredicate), payment.creationTime.asc());

		// than
		assertThat(clientPayments, is(notNullValue()));
		assertThat(collectionUtils.copyIterator(clientPayments.iterator()), hasSize(3));
	}
}