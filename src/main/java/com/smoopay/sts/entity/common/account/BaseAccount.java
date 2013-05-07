package com.smoopay.sts.entity.common.account;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.status.AccountStatus;

/**
 * http://www.hostettler.net/blog/2012/03/22/one-to-one-relations-in-jpa-2-dot-0
 * /
 * 
 * A BaseAccount.
 * 
 * @author Adam Dec
 * 
 */
@MappedSuperclass
public class BaseAccount extends AbstractEntity {

	/**
	 * Internal virtual account number - used in entire system to make actual
	 * payments
	 */
	private String virtualAccNo;

	private AccountStatus accountStatus;

	private Currency currency;

	@Temporal(TemporalType.TIMESTAMP)
	private Date openingDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expiryDate;

	/**
	 * Creates a new {@link BaseAccount} from the given accountNumber,
	 * accountStatus, currency.
	 * 
	 * @param virtualAccNo
	 *            must not be {@literal null} or empty.
	 * @param accountStatus
	 *            must not be {@literal null}.
	 * @param currency
	 *            must not be {@literal null}.
	 */
	public BaseAccount(String virtualAccNo, AccountStatus accountStatus, Currency currency) {
		Assert.hasText(virtualAccNo);
		Assert.notNull(accountStatus);
		Assert.notNull(currency);
		this.virtualAccNo = virtualAccNo;
		this.accountStatus = accountStatus;
		this.currency = currency;
		this.openingDate = new Date();
	}

	protected BaseAccount() {
	}

	/**
	 * Returns the expiryDate.
	 * 
	 * @return expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiryDate of the {@link BaseAccount}.
	 * 
	 * @param expiryDate
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Sets the accountStatus of the {@link BaseAccount}.
	 * 
	 * @param accountStatus
	 */
	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	/**
	 * Returns the virtualAccNo.
	 * 
	 * @return virtualAccNo
	 */
	public String getVirtualAccNo() {
		return virtualAccNo;
	}

	/**
	 * Returns the accountStatus.
	 * 
	 * @return accountStatus
	 */
	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	/**
	 * Returns the currency.
	 * 
	 * @return currency
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * Returns the openingDate.
	 * 
	 * @return openingDate
	 */
	public Date getOpeningDate() {
		return openingDate;
	}
}