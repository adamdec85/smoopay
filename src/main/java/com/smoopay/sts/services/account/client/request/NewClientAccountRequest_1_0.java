package com.smoopay.sts.services.account.client.request;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.common.validator.currency.Currency;

@JsonAutoDetect
public class NewClientAccountRequest_1_0 {

	@NotNull(message = "ClientId can not be NULL")
	private Long clientId;

	@NotNull(message = "Currency can not be NULL")
	@Currency(message = "Provided Currency ${value} must be: PLN, GBP, CHF, USD")
	private String currency;

	@NotNull(message = "BankName can not be NULL")
	private String bankName;

	@NotNull(message = "NRB number can not be NULL")
	private String accNrbNumber;

	public NewClientAccountRequest_1_0() {
	}

	public NewClientAccountRequest_1_0(Long clientId, String currency, String bankName, String accNrbNumber) {
		super();
		this.clientId = clientId;
		this.currency = currency;
		this.bankName = bankName;
		this.accNrbNumber = accNrbNumber;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccNrbNumber() {
		return accNrbNumber;
	}

	public void setAccNrbNumber(String accNrbNumber) {
		this.accNrbNumber = accNrbNumber;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}