package com.smoopay.sts.services.payment.pos.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.CurrencyEnum;

/**
 * ClientId - used to make payment history per client/per pos.
 * 
 * @author Adam Dec
 * 
 */
@JsonAutoDetect
public class NewPaymentRequest_1_0 {

	@NotNull(message = "merchantId can not be NULL")
	private Long merchantId;

	@NotNull(message = "posId can not be NULL")
	private Long posId;

	@NotNull(message = "amount can not be NULL")
	private BigDecimal amount;

	@NotNull(message = "currency can not be NULL")
	private CurrencyEnum currency;

	private Date paymentDate;

	public NewPaymentRequest_1_0() {
		super();
	}

	public NewPaymentRequest_1_0(Long merchantId, Long posId, BigDecimal amount, CurrencyEnum currency, Date paymentDate) {
		super();
		this.merchantId = merchantId;
		this.posId = posId;
		this.amount = amount;
		this.currency = currency;
		this.paymentDate = paymentDate;
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