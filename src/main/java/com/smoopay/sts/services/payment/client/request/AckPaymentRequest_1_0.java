package com.smoopay.sts.services.payment.client.request;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.dto.CurrencyEnum;

/**
 * ClientId - used to make payment history per client/per POS. <br />
 * VirtualAccNo - used to find proper account (client sees the list of the
 * accounts that he wants to credit) <br />
 * 
 * @author Adam Dec
 * 
 */
@JsonAutoDetect
public class AckPaymentRequest_1_0 {

	@NotNull(message = "ClientId can not be NULL!")
	private Long clientId;

	@NotNull(message = "VirtualAccNo can not be NULL!")
	private String virtualAccNo;

	@NotNull(message = "MerchantId can not be NULL!")
	private Long merchantId;

	@NotNull(message = "PosId can not be NULL!")
	private Long posId;

	@NotNull(message = "Amount can not be NULL!")
	private BigDecimal amount;

	@NotNull(message = "Currency can not be NULL!")
	private CurrencyEnum currency;

	private Date paymentDate;

	public AckPaymentRequest_1_0() {
		super();
	}

	public AckPaymentRequest_1_0(Long clientId, String virtualAccNo, Long merchantId, Long posId, BigDecimal amount, CurrencyEnum currency, Date paymentDate) {
		super();
		this.clientId = clientId;
		this.virtualAccNo = virtualAccNo;
		this.merchantId = merchantId;
		this.posId = posId;
		this.amount = amount;
		this.currency = currency;
		this.paymentDate = paymentDate;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
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

	public String getVirtualAccNo() {
		return virtualAccNo;
	}

	public void setVirtualAccNo(String virtualAccNo) {
		this.virtualAccNo = virtualAccNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}