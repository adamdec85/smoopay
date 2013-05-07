package com.smoopay.sts.services.payment.pos.request;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class CancelPaymentRequest_1_0 {

	@NotNull(message = "paymentId can not be NULL")
	private Long paymentId;

	/**
	 * For statistics.
	 */
	private String reason;

	public CancelPaymentRequest_1_0() {
		super();
	}

	public CancelPaymentRequest_1_0(Long paymentId) {
		super();
		this.paymentId = paymentId;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}