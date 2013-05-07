package com.smoopay.sts.integration.payment.validator;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.smoopay.sts.aop.logging.LogLevel;
import com.smoopay.sts.aop.logging.Loggable;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.dao.payment.PaymentCustomRepository;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.integration.payment.gateway.IPaymentAckClientGatewayHistory;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.services.payment.client.response.AckPaymentResponse_1_0;
import com.smoopay.sts.services.rest.response.ResponseStatus;

@Component
public class PaymentAckClientValidator {

	@Autowired
	private PaymentCustomRepository paymentCustomRepository;

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Autowired
	private IPaymentAckClientGatewayHistory paymentAckClientGatewayHistory;

	@Loggable(LogLevel.DEBUG)
	public Message<AckPaymentResponse_1_0> validateClientAckPayment(Message<AckPaymentRequest_1_0> requestMessage) {
		final AckPaymentRequest_1_0 request = (AckPaymentRequest_1_0) requestMessage.getPayload();
		final ClientAccount clientAccount = clientCustomRepository.getClientAccountByVirtualAccNo(request.getClientId(), request.getVirtualAccNo());

		if (clientAccount == null) {
			AckPaymentResponse_1_0 errorResponse = new AckPaymentResponse_1_0(-1L, PaymentStatusEnum.REJECTED);
			errorResponse.setResponseStatus(ResponseStatus.ERROR);
			errorResponse.setReasonText("Can not find client account with virtualAccNo=" + request.getVirtualAccNo());
			return MessageBuilder.withPayload(errorResponse).build();
		}

		if (clientAccount.getClientFinancialBalance().getPrimaryAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
			AckPaymentResponse_1_0 errorResponse = new AckPaymentResponse_1_0(-1L, PaymentStatusEnum.REJECTED);
			errorResponse.setResponseStatus(ResponseStatus.ERROR);
			errorResponse.setReasonText("Insufficient funds for cash account=" + clientAccount.getClientAccountData().getBankName());
			return MessageBuilder.withPayload(errorResponse).build();
		}
		AckPaymentResponse_1_0 response = paymentCustomRepository.ackPendingPaymentForGivenPos(request);
		if (ResponseStatus.OK.equals(response.getResponseStatus())) {
			// Send async new payment for client - history
			// TODO: Check if this will be performed in separate transaction
			paymentAckClientGatewayHistory.saveClientAckPayment(requestMessage);
		}
		return MessageBuilder.withPayload(response).build();
	}
}