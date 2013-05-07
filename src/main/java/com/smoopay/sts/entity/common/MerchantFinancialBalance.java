package com.smoopay.sts.entity.common;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;

@Entity
public class MerchantFinancialBalance extends AbstractEntity {

	private BigDecimal primaryAccountBalance;
	private BigDecimal lockedBalance;

	public MerchantFinancialBalance(BigDecimal primaryAccountBalance, BigDecimal lockedBalance) {
		Assert.notNull(primaryAccountBalance, "PrimaryAccountBalance must not be null!");
		Assert.isTrue(primaryAccountBalance.doubleValue() > 0);
		Assert.notNull(lockedBalance, "LockedBalance must not be null!");
		Assert.isTrue(lockedBalance.doubleValue() > 0);
		this.primaryAccountBalance = primaryAccountBalance;
		this.primaryAccountBalance = lockedBalance;
	}

	public MerchantFinancialBalance(BigDecimal primaryAccountBalance) {
		Assert.notNull(primaryAccountBalance, "PrimaryAccountBalance must not be null!");
		this.primaryAccountBalance = primaryAccountBalance;
		this.primaryAccountBalance = BigDecimal.ZERO;
	}

	protected MerchantFinancialBalance() {
	}

	/**
	 * Gets the primaryAccountBalance.
	 * 
	 * @return the primaryAccountBalance
	 */
	public BigDecimal getPrimaryAccountBalance() {
		return primaryAccountBalance;
	}

	/**
	 * Gets the lockedBalance.
	 * 
	 * @return the lockedBalance
	 */
	public BigDecimal getLockedBalance() {
		return lockedBalance;
	}

	public void setPrimaryAccountBalance(BigDecimal primaryAccountBalance) {
		this.primaryAccountBalance = primaryAccountBalance;
	}

	public void setLockedBalance(BigDecimal lockedBalance) {
		this.lockedBalance = lockedBalance;
	}

	/**
	 * Returns a copy of the current {@link MerchantFinancialBalance} instance
	 * which is a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public MerchantFinancialBalance getCopy() {
		return new MerchantFinancialBalance(this.primaryAccountBalance, this.lockedBalance);
	}

	@Override
	public String toString() {
		return "MerchantFinancialBalance [primaryAccountBalance=" + primaryAccountBalance + ", lockedBalance=" + lockedBalance + "]";
	}
}