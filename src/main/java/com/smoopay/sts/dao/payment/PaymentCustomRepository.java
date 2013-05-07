package com.smoopay.sts.dao.payment;

import com.smoopay.sts.entity.payments.Payment;
import com.smoopay.sts.services.payment.client.request.AckPaymentRequest_1_0;
import com.smoopay.sts.services.payment.client.response.AckPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.request.AmendPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.CancelPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.request.NewPaymentRequest_1_0;
import com.smoopay.sts.services.payment.pos.response.AmendPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.CancelPaymentResponse_1_0;
import com.smoopay.sts.services.payment.pos.response.NewPaymentResponse_1_0;

public interface PaymentCustomRepository {

	NewPaymentResponse_1_0 createOrAddNewPayment(NewPaymentRequest_1_0 request);

	NewPaymentResponse_1_0 checkPaymentStatus(Long paymentId);

	CancelPaymentResponse_1_0 cancelPayment(CancelPaymentRequest_1_0 request);

	AmendPaymentResponse_1_0 amendPayment(AmendPaymentRequest_1_0 request);

	AckPaymentResponse_1_0 ackPendingPaymentForGivenPos(AckPaymentRequest_1_0 request);

	Payment findPendingPaymentByMerchantIdAndPosId(Long merchantId, Long posId);
}