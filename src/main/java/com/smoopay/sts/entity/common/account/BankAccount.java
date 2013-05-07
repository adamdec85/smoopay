package com.smoopay.sts.entity.common.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.smoopay.sts.entity.bank.Bank;
import com.smoopay.sts.entity.common.BankFinancialBalance;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.data.BankAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;

/**
 * 
 * A BankAccount.
 * 
 * @author Adam Dec
 * 
 */
@Entity
@Table(indexes = { @Index(name = "bankVirtualAccNoIndex", columnNames = { "virtualAccNo" }),
		@Index(name = "bankAccStatusIndex", columnNames = { "accountStatus" }), @Index(name = "bankAccCurrencyIndex", columnNames = { "currency" }),
		@Index(name = "bankAccOpeningDateIndex", columnNames = { "openingDate" }) }, appliesTo = "BankAccount")
public class BankAccount extends BaseAccount {

	@ManyToOne
	private Bank bank;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bankAccountData_id")
	@Fetch(FetchMode.JOIN)
	private BankAccountData bankAccountData;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bankFinancialBalance_id")
	private BankFinancialBalance bankFinancialBalance;

	public BankAccount(String virtualAccNo, AccountStatus accountStatus, Currency currency) {
		super(virtualAccNo, accountStatus, currency);
	}

	protected BankAccount() {
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public BankAccountData getBankAccountData() {
		return bankAccountData;
	}

	public void setBankAccountData(BankAccountData bankAccountData) {
		this.bankAccountData = bankAccountData;
	}

	public BankFinancialBalance getBankFinancialBalance() {
		return bankFinancialBalance;
	}

	public void setBankFinancialBalance(BankFinancialBalance bankFinancialBalance) {
		this.bankFinancialBalance = bankFinancialBalance;
	}
}