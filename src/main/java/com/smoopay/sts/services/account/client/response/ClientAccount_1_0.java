package com.smoopay.sts.services.account.client.response;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.account.AccountStatusEnum;

@JsonAutoDetect
public class ClientAccount_1_0 {

	@NotNull(message = "VirtualAccNo can not be NULL")
	private String virtualAccNo;

	@NotNull(message = "AccountStatus can not be NULL")
	private AccountStatusEnum accountStatus;

	@NotNull(message = "Currency can not be NULL")
	private CurrencyEnum currency;

	@NotNull(message = "OpeningDate can not be NULL")
	private String openingDate;

	private String expiryDate;

	@NotNull(message = "CashAccNRB can not be NULL")
	private String cashAccNRB;

	@NotNull(message = "CashAccIBAN can not be NULL")
	private String cashAccIBAN;

	@NotNull(message = "PrimaryAccountBalance can not be NULL")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal primaryAccountBalance;

	@NotNull(message = "LockedBalance can not be NULL")
	@NumberFormat(style = Style.CURRENCY)
	private BigDecimal lockedBalance;

	public ClientAccount_1_0() {
	}

	public ClientAccount_1_0(String virtualAccNo, AccountStatusEnum accountStatus, CurrencyEnum currency, String openingDate, String expiryDate,
			String cashAccNRB, String cashAccIBAN, BigDecimal primaryAccountBalance, BigDecimal lockedBalance) {
		super();
		this.virtualAccNo = virtualAccNo;
		this.accountStatus = accountStatus;
		this.currency = currency;
		this.openingDate = openingDate;
		this.expiryDate = expiryDate;
		this.cashAccNRB = cashAccNRB;
		this.cashAccIBAN = cashAccIBAN;
		this.primaryAccountBalance = primaryAccountBalance;
		this.lockedBalance = lockedBalance;
	}

	public String getVirtualAccNo() {
		return virtualAccNo;
	}

	public void setVirtualAccNo(String virtualAccNo) {
		this.virtualAccNo = virtualAccNo;
	}

	public AccountStatusEnum getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatusEnum accountStatus) {
		this.accountStatus = accountStatus;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCashAccNRB() {
		return cashAccNRB;
	}

	public void setCashAccNRB(String cashAccNRB) {
		this.cashAccNRB = cashAccNRB;
	}

	public String getCashAccIBAN() {
		return cashAccIBAN;
	}

	public void setCashAccIBAN(String cashAccIBAN) {
		this.cashAccIBAN = cashAccIBAN;
	}

	public BigDecimal getPrimaryAccountBalance() {
		return primaryAccountBalance;
	}

	public void setPrimaryAccountBalance(BigDecimal primaryAccountBalance) {
		this.primaryAccountBalance = primaryAccountBalance;
	}

	public BigDecimal getLockedBalance() {
		return lockedBalance;
	}

	public void setLockedBalance(BigDecimal lockedBalance) {
		this.lockedBalance = lockedBalance;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
