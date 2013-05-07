package com.smoopay.sts.integration.payment.gateway;

import java.util.concurrent.Future;

import org.springframework.integration.Message;

import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;

public interface IPaymentAckClientGatewayHistory {

	Future<Boolean> saveClientAckPayment(Message<AckPaymentRequest_1_0> requestMessage);
}