package com.smoopay.sts.entity.common.address;

import javax.persistence.Entity;

/**
 * An ClientAddress.
 * 
 * @author Adam Dec
 */
@Entity
public class ClientAddress extends BaseAddress {

	public ClientAddress(String street, String city, String postCode, String country, boolean resident) {
		super(street, city, postCode, country, resident);
	}

	protected ClientAddress() {
	}

	/**
	 * Returns a copy of the current {@link ClientAddress} instance which is a
	 * new entity in terms of persistence.
	 * 
	 * @return new ClientAddress
	 */
	@Override
	public ClientAddress getCopy() {
		return new ClientAddress(getStreet(), getCity(), getPostCode(), getCountry(), isResident());
	}
}