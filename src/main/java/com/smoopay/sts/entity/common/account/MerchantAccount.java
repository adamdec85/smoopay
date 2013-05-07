package com.smoopay.sts.entity.common.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.MerchantFinancialBalance;
import com.smoopay.sts.entity.common.account.data.MerchantAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;
import com.smoopay.sts.entity.merchant.Merchant;

/**
 * http://www.hostettler.net/blog/2012/03/22/one-to-one-relations-in-jpa-2-dot-0
 * /
 * 
 * A ClientAccount.
 * 
 * @author Adam Dec
 * 
 */
@Entity
@Table(indexes = { @Index(name = "merchantVirtualAccNoIndex", columnNames = { "virtualAccNo" }),
		@Index(name = "merchantAccStatusIndex", columnNames = { "accountStatus" }), @Index(name = "merchantAccCurrencyIndex", columnNames = { "currency" }),
		@Index(name = "merchantAccOpeningDateIndex", columnNames = { "openingDate" }) }, appliesTo = "MerchantAccount")
public class MerchantAccount extends BaseAccount {

	@OneToOne
	@JoinColumn(name = "merchant_id", referencedColumnName = "id")
	private Merchant merchant;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "merchantAccountData_id")
	@Fetch(FetchMode.JOIN)
	private MerchantAccountData merchantAccountData;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "merchantFinancialBalance_id")
	private MerchantFinancialBalance merchantFinancialBalance;

	public MerchantAccount(String virtualAccNo, AccountStatus accountStatus, Currency currency) {
		super(virtualAccNo, accountStatus, currency);
	}

	protected MerchantAccount() {
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public MerchantAccountData getMerchantAccountData() {
		return merchantAccountData;
	}

	public void setMerchantAccountData(MerchantAccountData merchantAccountData) {
		this.merchantAccountData = merchantAccountData;
	}

	public MerchantFinancialBalance getMerchantFinancialBalance() {
		return merchantFinancialBalance;
	}

	public void setMerchantFinancialBalance(MerchantFinancialBalance merchantFinancialBalance) {
		this.merchantFinancialBalance = merchantFinancialBalance;
	}
}