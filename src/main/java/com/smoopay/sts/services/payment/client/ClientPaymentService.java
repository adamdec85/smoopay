package com.smoopay.sts.services.payment.client;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smoopay.sts.aop.logging.LogLevel;
import com.smoopay.sts.aop.logging.Loggable;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.integration.payment.gateway.IPaymentAckClientGateway;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.services.payment.client.response.AckPaymentResponse_1_0;
import com.smoopay.sts.services.payment.client.response.PaymentResponse_1_0;

@Controller
@RequestMapping("/payment/client/v1")
@Transactional
public class ClientPaymentService {

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Autowired
	private IPaymentAckClientGateway paymentAckClientGateway;

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/secured_ack", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	AckPaymentResponse_1_0 securedAckPayment(@RequestBody AckPaymentRequest_1_0 request) {
		Message<?> received = paymentAckClientGateway.validateClientAckPayment(MessageBuilder.withPayload(request).build());
		return (AckPaymentResponse_1_0) received.getPayload();
	}

	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/ack", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	AckPaymentResponse_1_0 ackPayment(@RequestBody AckPaymentRequest_1_0 request) {
		Message<?> received = paymentAckClientGateway.validateClientAckPayment(MessageBuilder.withPayload(request).build());
		return (AckPaymentResponse_1_0) received.getPayload();
	}

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "/get/{clientId}/{from}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<PaymentResponse_1_0> getPayments(@NotNull @PathVariable Long clientId,
			@NotNull @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy kk:mm:ss") Date from) {
		return clientCustomRepository.findClientPaymentsFromDate(clientId, from);
	}
}