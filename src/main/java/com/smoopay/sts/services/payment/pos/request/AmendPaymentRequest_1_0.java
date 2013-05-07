package com.smoopay.sts.services.payment.pos.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.CurrencyEnum;

@JsonAutoDetect
public class AmendPaymentRequest_1_0 {

	@NotNull(message = "merchantId can not be NULL")
	private Long merchantId;

	@NotNull(message = "posId can not be NULL")
	private Long posId;

	@NotNull(message = "paymentId can not be NULL")
	private Long paymentId;

	@NotNull(message = "amount can not be NULL")
	private BigDecimal amount;

	@NotNull(message = "currency can not be NULL")
	private CurrencyEnum currency;

	public AmendPaymentRequest_1_0() {
		super();
	}

	public AmendPaymentRequest_1_0(Long merchantId, Long posId, Long paymentId, BigDecimal amount, CurrencyEnum currency) {
		super();
		this.merchantId = merchantId;
		this.posId = posId;
		this.paymentId = paymentId;
		this.amount = amount;
		this.currency = currency;
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

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getPosId() {
		return posId;
	}

	public void setPosId(Long posId) {
		this.posId = posId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}