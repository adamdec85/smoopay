package com.smoopay.sts.services.payment.client.response;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.services.rest.response.BaseResponse;

@JsonAutoDetect
public class PaymentResponse_1_0 extends BaseResponse {

	@NotNull(message = "paymentId can not be NULL")
	private Long paymentId;

	@NotNull(message = "merchant can not be NULL")
	private String merchant;

	@NotNull(message = "amount can not be NULL")
	private BigDecimal amount;

	@NotNull(message = "currency can not be NULL")
	private CurrencyEnum currency;

	@NotNull(message = "paymentStatus can not be NULL")
	private PaymentStatusEnum paymentStatus;

	@NotNull(message = "paymentDate can not be NULL")
	private Date paymentDate;

	public PaymentResponse_1_0() {
		super();
	}

	public PaymentResponse_1_0(Long paymentId, String merchant, BigDecimal amount, CurrencyEnum currency, PaymentStatusEnum paymentStatus, Date paymentDate) {
		super();
		this.paymentId = paymentId;
		this.merchant = merchant;
		this.amount = amount;
		this.currency = currency;
		this.paymentStatus = paymentStatus;
		this.paymentDate = paymentDate;
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

	public PaymentStatusEnum getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusEnum paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}