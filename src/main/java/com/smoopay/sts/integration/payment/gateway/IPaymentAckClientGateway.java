package com.smoopay.sts.integration.payment.gateway;

import org.springframework.integration.Message;

import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.services.payment.client.response.AckPaymentResponse_1_0;

public interface IPaymentAckClientGateway {

	Message<AckPaymentResponse_1_0> validateClientAckPayment(Message<AckPaymentRequest_1_0> requestMessage);
}