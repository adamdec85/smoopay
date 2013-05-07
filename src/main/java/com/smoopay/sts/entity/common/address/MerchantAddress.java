package com.smoopay.sts.entity.common.address;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.smoopay.sts.entity.merchant.Merchant;

/**
 * An address.
 * 
 * @author Adam Dec
 */
@Entity
public class MerchantAddress extends BaseAddress {

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "merchant_id", referencedColumnName = "id")
	private Merchant merchant;

	public MerchantAddress(String street, String city, String postCode, String country, boolean resident) {
		super(street, city, postCode, country, resident);
	}

	protected MerchantAddress() {
	}

	/**
	 * Returns a copy of the current {@link MerchantAddress} instance which is a
	 * new entity in terms of persistence.
	 * 
	 * @return new MerchantAddress
	 */
	@Override
	public MerchantAddress getCopy() {
		return new MerchantAddress(getStreet(), getCity(), getPostCode(), getCountry(), isResident());
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
}