package com.smoopay.sts.services.payment.client.response;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.services.rest.response.BaseResponse;

@JsonAutoDetect
public class AckPaymentResponse_1_0 extends BaseResponse {

	@NotNull(message = "paymentId can not be NULL")
	private Long paymentId;

	@NotNull(message = "paymentStatus can not be NULL")
	private PaymentStatusEnum paymentStatus;

	public AckPaymentResponse_1_0() {
		super();
	}

	public AckPaymentResponse_1_0(Long paymentId, PaymentStatusEnum paymentStatus) {
		this.paymentId = paymentId;
		this.paymentStatus = paymentStatus;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public PaymentStatusEnum getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusEnum paymentStatusEnum) {
		this.paymentStatus = paymentStatusEnum;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}