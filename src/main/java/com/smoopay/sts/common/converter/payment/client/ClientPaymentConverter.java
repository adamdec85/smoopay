package com.smoopay.sts.common.converter.payment.client;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.entity.payments.ClientPayment;
import com.smoopay.sts.services.payment.client.response.PaymentResponse_1_0;

@Component
public class ClientPaymentConverter implements Converter<ClientPayment, PaymentResponse_1_0> {

	public PaymentResponse_1_0 convert(ClientPayment clientPayment) {
		return new PaymentResponse_1_0(clientPayment.getId(), "TODO_PUT_MERCHANT_ID", clientPayment.getAmount(), clientPayment.getCurrency().getEnumValue(),
				clientPayment.getPaymentStatus().getEnumValue(), clientPayment.getCreationTime());
	}
}