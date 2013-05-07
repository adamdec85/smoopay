package com.smoopay.sts.entity.common.address;

import javax.persistence.Entity;

/**
 * An BankAddress.
 * 
 * @author Adam Dec
 */
@Entity
public class BankAddress extends BaseAddress {

	public BankAddress(String street, String city, String postCode, String country, boolean resident) {
		super(street, city, postCode, country, resident);
	}

	protected BankAddress() {
	}

	/**
	 * Returns a copy of the current {@link BankAddress} instance which is a new
	 * entity in terms of persistence.
	 * 
	 * @return new BankAddress
	 */
	@Override
	public BankAddress getCopy() {
		return new BankAddress(getStreet(), getCity(), getPostCode(), getCountry(), isResident());
	}
}