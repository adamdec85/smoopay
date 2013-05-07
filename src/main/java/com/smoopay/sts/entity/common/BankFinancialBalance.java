package com.smoopay.sts.entity.common;

import java.math.BigDecimal;

import javax.persistence.Entity;

import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;

@Entity
public class BankFinancialBalance extends AbstractEntity {

	private BigDecimal primaryAccountBalance;
	private BigDecimal lockedBalance;

	public BankFinancialBalance(BigDecimal primaryAccountBalance, BigDecimal lockedBalance) {
		Assert.notNull(primaryAccountBalance, "PrimaryAccountBalance must not be null!");
		Assert.isTrue(primaryAccountBalance.doubleValue() > 0);
		Assert.notNull(lockedBalance, "LockedBalance must not be null!");
		Assert.isTrue(lockedBalance.doubleValue() > 0);
		this.primaryAccountBalance = primaryAccountBalance;
		this.primaryAccountBalance = lockedBalance;
	}

	public BankFinancialBalance(BigDecimal primaryAccountBalance) {
		Assert.notNull(primaryAccountBalance, "PrimaryAccountBalance must not be null!");
		this.primaryAccountBalance = primaryAccountBalance;
		this.primaryAccountBalance = BigDecimal.ZERO;
	}

	protected BankFinancialBalance() {
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
	 * Returns a copy of the current {@link BankFinancialBalance} instance which
	 * is a new entity in terms of persistence.
	 * 
	 * @return
	 */
	public BankFinancialBalance getCopy() {
		return new BankFinancialBalance(this.primaryAccountBalance, this.lockedBalance);
	}

	@Override
	public String toString() {
		return "BankFinancialBalance [primaryAccountBalance=" + primaryAccountBalance + ", lockedBalance=" + lockedBalance + "]";
	}
}