package com.smoopay.sts.entity.common.address;

import java.util.regex.Pattern;

import javax.persistence.MappedSuperclass;

import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;

/**
 * An address.
 * 
 * @author Adam Dec
 */
@MappedSuperclass
public class BaseAddress extends AbstractEntity {

	private static final String POSTCODE_REGEX = "^\\d{2}-\\d{3}$";
	private static final Pattern PATTERN = Pattern.compile(POSTCODE_REGEX);

	private String street;
	private String city;
	private String postCode;
	private String country;
	private boolean resident;

	/**
	 * Creates a new {@link BaseAddress} from the given street, city and
	 * country.
	 * 
	 * @param street
	 *            must not be {@literal null} or empty.
	 * @param city
	 *            must not be {@literal null} or empty.
	 * @param postCode
	 *            must not be {@literal null} or empty.
	 * @param country
	 *            must not be {@literal null} or empty.
	 */
	public BaseAddress(String street, String city, String postCode, String country, boolean resident) {
		Assert.hasText(street, "Street must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(postCode, "PostCode must not be null or empty!");
		Assert.isTrue(isValid(postCode), "Invalid postCode!");
		Assert.hasText(country, "Country must not be null or empty!");
		this.street = street;
		this.city = city;
		this.postCode = postCode;
		this.country = country;
		this.resident = resident;
	}

	protected BaseAddress() {
	}

	/**
	 * Returns the street.
	 * 
	 * @return street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Returns the city.
	 * 
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the post code.
	 * 
	 * @return postCode
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Returns the country.
	 * 
	 * @return country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Returns the resident flag.
	 * 
	 * @return resident
	 */
	public boolean isResident() {
		return resident;
	}

	/**
	 * Returns a copy of the current {@link BaseAddress} instance which is a new
	 * entity in terms of persistence.
	 * 
	 * @return new address
	 */
	public BaseAddress getCopy() {
		return new BaseAddress(this.street, this.city, this.postCode, this.country, this.resident);
	}

	/**
	 * Returns whether the given {@link String} is a valid {@link postCode}
	 * which means you can safely instantiate the class.
	 * 
	 * @param candidate
	 */
	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}
}