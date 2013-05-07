package com.smoopay.sts.entity.merchant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;
import com.smoopay.sts.entity.common.CompanyRegistry;
import com.smoopay.sts.entity.common.EmailAddress;
import com.smoopay.sts.entity.common.account.MerchantAccount;
import com.smoopay.sts.entity.common.address.MerchantAddress;
import com.smoopay.sts.entity.payments.Payment;

/**
 * A Merchant.
 * 
 * What is the difference between Tesco in Krakow and Tesco in Rzeszow? Same
 * account?
 * 
 * @author Adam Dec
 */
@Entity
public class Merchant extends AbstractEntity {

	@Column(unique = true)
	private String name;

	@Column(unique = true)
	private EmailAddress emailAddress;

	private String telephone;

	private CompanyRegistry companyRegistry;

	private String www;

	private boolean enabled;

	@OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
	private MerchantAccount merchantAccount;

	@OneToOne(mappedBy = "merchant", cascade = CascadeType.ALL, orphanRemoval = true)
	private MerchantAddress merchantAddress;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "merchant_id")
	@Fetch(FetchMode.JOIN)
	private Set<Payment> payments = new HashSet<>();

	protected Merchant() {
	}

	/**
	 * Creates a new {@link Merchant}.
	 * 
	 * @param name
	 *            must not be {@literal null} or empty.
	 * @param krs
	 *            must not be {@literal null} or empty.
	 * @param regon
	 *            must not be {@literal null} or empty.
	 * @param nip
	 *            must not be {@literal null} or empty.
	 * @param merchantAccount
	 *            must not be {@literal null}.
	 * @param merchantAddress
	 *            must not be {@literal null}.
	 */
	public Merchant(String name, String krs, String regon, String nip, MerchantAccount merchantAccount, MerchantAddress merchantAddress) {
		super();
		Assert.hasText(name);
		Assert.notNull(merchantAccount);
		Assert.notNull(merchantAddress);
		this.name = name;
		this.companyRegistry = new CompanyRegistry(krs, regon, nip);
		this.merchantAccount = merchantAccount;
		this.merchantAddress = merchantAddress;
	}

	/**
	 * Adds the given {@link Payment} to the {@link Merchant}.
	 * 
	 * @param payment
	 *            must not be {@literal null}.
	 */
	public void add(Payment payment) {
		Assert.notNull(payments);
		this.payments.add(payment);
	}

	/**
	 * Returns the payments of the {@link Merchant}.
	 * 
	 * @return
	 */
	public Set<Payment> getPayments() {
		return payments;
	}

	public Set<Payment> getPaymentsSafe() {
		return Collections.unmodifiableSet(payments);
	}

	/**
	 * Sets the {@link Merchant}'s {@link Payment}.
	 * 
	 * @param payments
	 *            must not be {@literal null}.
	 */
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}

	/**
	 * Removes the {@link Merchant}'s payments.
	 */
	public void removePayments() {
		payments.clear();
	}

	public String getName() {
		return name;
	}

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public CompanyRegistry getCompanyRegistry() {
		return companyRegistry;
	}

	public void setCompanyRegistry(CompanyRegistry companyRegistry) {
		this.companyRegistry = companyRegistry;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public MerchantAccount getMerchantAccount() {
		return merchantAccount;
	}

	public void setMerchantAccount(MerchantAccount merchantAccount) {
		this.merchantAccount = merchantAccount;
	}

	public MerchantAddress getMerchantAddress() {
		return merchantAddress;
	}

	public void setMerchantAddress(MerchantAddress merchantAddress) {
		this.merchantAddress = merchantAddress;
	}
}