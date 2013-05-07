package com.smoopay.sts.entity.bank;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Index;
import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;
import com.smoopay.sts.entity.common.CompanyRegistry;
import com.smoopay.sts.entity.common.EmailAddress;
import com.smoopay.sts.entity.common.account.BankAccount;
import com.smoopay.sts.entity.common.address.BankAddress;

/**
 * A Bank.
 * 
 * @author Adam Dec
 */
@Entity
public class Bank extends AbstractEntity {

	@Index(name = "bankNameIndex", columnNames = "name")
	private String name;

	@Column(unique = true)
	private EmailAddress emailAddress;

	private String telephone;

	private CompanyRegistry companyRegistry;

	private String www;

	@Index(name = "bankSwiftIndex", columnNames = "swift")
	private String swift;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bank_id")
	private Set<BankAccount> bankAccounts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "bank_id")
	private Set<BankAddress> addresses = new HashSet<>();

	/**
	 * Creates a new {@link Bank}.
	 * 
	 * @param name
	 *            must not be {@literal null} or empty.
	 * @param telephone
	 *            must not be {@literal null} or empty.
	 * @param krs
	 *            must not be {@literal null} or empty.
	 * @param regon
	 *            must not be {@literal null} or empty.
	 * @param nip
	 *            must not be {@literal null} or empty.
	 * @param www
	 *            must not be {@literal null} or empty.
	 * @param swift
	 *            must not be {@literal null} or empty.
	 */
	public Bank(String name, String telephone, String krs, String regon, String nip, String www, String swift) {
		Assert.hasText(name);
		Assert.hasText(telephone);
		Assert.hasText(www);
		Assert.hasText(swift);
		this.name = name;
		this.telephone = telephone;
		this.companyRegistry = new CompanyRegistry(krs, regon, nip);
		this.www = www;
		this.swift = swift;
	}

	protected Bank() {
	}

	/**
	 * Adds the given {@link BankAddress} to the {@link Bank}.
	 * 
	 * @param address
	 *            must not be {@literal null}.
	 */
	public void add(BankAddress address) {
		Assert.notNull(address);
		this.addresses.add(address);
	}

	/**
	 * Adds the given {@link BankAccount} to the {@link Bank}.
	 * 
	 * @param bankAccount
	 *            must not be {@literal null}.
	 */
	public void add(BankAccount bankAccount) {
		Assert.notNull(bankAccount);
		this.bankAccounts.add(bankAccount);
	}

	/**
	 * Returns the name of the {@link Bank}.
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the {@link Bank}.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the firstName of the {@link Bank}.
	 * 
	 * @return
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the {@link Bank}'s {@link EmailAddress}.
	 * 
	 * @param emailAddress
	 *            must not be {@literal null}.
	 */
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Returns the telephone of the {@link Bank}.
	 * 
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Sets the telephone of the {@link Bank}.
	 * 
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public CompanyRegistry getCompanyRegistry() {
		return companyRegistry;
	}

	public void setCompanyRegistry(CompanyRegistry companyRegistry) {
		this.companyRegistry = companyRegistry;
	}

	/**
	 * Returns the www of the {@link Bank}.
	 * 
	 * @return
	 */
	public String getWww() {
		return www;
	}

	/**
	 * Sets the www of the {@link Bank}.
	 * 
	 * @param www
	 */
	public void setWww(String www) {
		this.www = www;
	}

	/**
	 * Returns the swift of the {@link Bank}.
	 * 
	 * @return
	 */
	public String getSwift() {
		return swift;
	}

	/**
	 * Sets the swift of the {@link Bank}.
	 * 
	 * @param swift
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	/**
	 * Returns the bankAccounts of the {@link Bank}.
	 * 
	 * @return
	 */
	public Set<BankAccount> getBankAccounts() {
		return Collections.unmodifiableSet(bankAccounts);
	}

	/**
	 * Sets the {@link Bank}'s {@link BankAccount}.
	 * 
	 * @param bankAccounts
	 *            must not be {@literal null}.
	 */
	public void setBankAccounts(Set<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	/**
	 * Returns the bankAccounts of the {@link Bank}.
	 * 
	 * @return
	 */
	public Set<BankAddress> getAddressesSafe() {
		return Collections.unmodifiableSet(addresses);
	}

	public Set<BankAddress> getAddresses() {
		return addresses;
	}

	/**
	 * Sets the {@link Bank}'s {@link BankAddress}.
	 * 
	 * @param addresses
	 *            must not be {@literal null}.
	 */
	public void setAddresses(Set<BankAddress> addresses) {
		this.addresses = addresses;
	}

	/**
	 * Removes the {@link Bank}'s addresses.
	 */
	public void removeAddresses() {
		addresses.clear();
	}

	/**
	 * Removes the {@link Bank}'s clientAccounts.
	 */
	public void removeBankAccounts() {
		bankAccounts.clear();
	}
}