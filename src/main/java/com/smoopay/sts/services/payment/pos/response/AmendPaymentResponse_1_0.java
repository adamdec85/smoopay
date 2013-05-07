package com.smoopay.sts.services.payment.pos.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.services.rest.response.BaseResponse;

@JsonAutoDetect
public class AmendPaymentResponse_1_0 extends BaseResponse {

	@NotNull(message = "paymentId can not be NULL")
	private Long paymentId;

	@NotNull(message = "amount can not be NULL")
	private BigDecimal amount;

	@NotNull(message = "currency can not be NULL")
	private CurrencyEnum currency;

	@NotNull(message = "paymentStatus can not be NULL")
	private PaymentStatusEnum amendPaymentStatus;

	public AmendPaymentResponse_1_0() {
		super();
	}

	public AmendPaymentResponse_1_0(Long paymentId, BigDecimal amount, CurrencyEnum currency) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.currency = currency;
		this.amendPaymentStatus = PaymentStatusEnum.PENDING;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public PaymentStatusEnum getAmendPaymentStatus() {
		return amendPaymentStatus;
	}

	public void setAmendPaymentStatus(PaymentStatusEnum amendPaymentStatus) {
		this.amendPaymentStatus = amendPaymentStatus;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).toString();
	}
}