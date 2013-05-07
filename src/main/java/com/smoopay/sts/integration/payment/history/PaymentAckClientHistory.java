package com.smoopay.sts.integration.payment.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.smoopay.sts.aop.logging.LogLevel;
import com.smoopay.sts.aop.logging.Loggable;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.payments.ClientPayment;
import com.smoopay.sts.repository.client.ClientPaymentRepository;
import com.smoopay.sts.repository.client.ClientRepository;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;

@Component
public class PaymentAckClientHistory {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientPaymentRepository clientPaymentRepository;

	@Loggable(LogLevel.DEBUG)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void saveClientAckPayment(Message<AckPaymentRequest_1_0> requestMessage) {
		final AckPaymentRequest_1_0 request = (AckPaymentRequest_1_0) requestMessage.getPayload();
		// Save new Client's payment
		final Client client = clientRepository.findOne(request.getClientId());
		final ClientPayment clientPayment = new ClientPayment(request.getAmount(), request.getCurrency());
		clientPayment.setClient(client);
		clientPaymentRepository.save(clientPayment);
	}
}