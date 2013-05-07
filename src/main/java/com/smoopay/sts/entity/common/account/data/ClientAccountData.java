package com.smoopay.sts.entity.common.account.data;

import javax.persistence.Entity;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;
import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;

/**
 * ClientAccountData.
 */
@Entity
@Table(indexes = { @Index(name = "clientAccBankNameIndex", columnNames = { "bankName" }), }, appliesTo = "ClientAccountData")
public class ClientAccountData extends AbstractEntity {

	private String bankName;

	/**
	 * Domestic payments. 12 1234 5678 1234 5678 9012 3456
	 * 
	 * C < --A--- > < ---------B--------->
	 * 
	 * A - numer rozliczeniowy B - numer rachunku bankowego C - liczba kontrolna
	 */
	private String cashAccNRB;

	/**
	 * International payments.
	 * 
	 * PL 12 1234 5678 1234 5678 9012 3456
	 * 
	 * D C < ---A--- > < ---------B--------->
	 * 
	 * A - numer rozliczeniowy B - numer rachunku bankowego C - liczba kontrolna
	 * D - kod kraju
	 */
	private String cashAccIBAN;

	public ClientAccountData(String bankName, String cashAccNRB, String cashAccIBAN) {
		Assert.hasText(bankName, "BankName must not be null or empty!");
		Assert.hasText(cashAccNRB, "CashAccNRB must not be null or empty!");
		Assert.hasText(cashAccIBAN, "CashAccIBAN must not be null or empty!");
		this.bankName = bankName;
		this.cashAccNRB = cashAccNRB;
		this.cashAccIBAN = cashAccIBAN;
	}

	protected ClientAccountData() {
	}

	/**
	 * Gets the cash account NRB number.
	 * 
	 * @return the cash account nrb
	 */
	public String getCashAccNRB() {
		return cashAccNRB;
	}

	/**
	 * Gets the cash account IBAN number.
	 * 
	 * @return the cash account iban
	 */
	public String getCashAccIBAN() {
		return cashAccIBAN;
	}

	/**
	 * Gets the cash account name.
	 * 
	 * @return the cash account name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Returns a copy of the current {@link ClientAccountData} instance which is
	 * a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public ClientAccountData getCopy() {
		return new ClientAccountData(this.bankName, this.cashAccNRB, this.cashAccIBAN);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}