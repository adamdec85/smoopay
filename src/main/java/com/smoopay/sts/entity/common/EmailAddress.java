package com.smoopay.sts.entity.common;

import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * A value object abstraction of an email address.
 * 
 * @author Adam Dec
 */
@Embeddable
public class EmailAddress {

	private static final String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

	@Column(name = "emailAddress")
	private String value;

	/**
	 * Creates a new {@link EmailAddress} from the given string source.
	 * 
	 * @param emailAddress
	 *            must not be {@literal null} or empty.
	 */
	public EmailAddress(String emailAddress) {
		Assert.isTrue(isValid(emailAddress), "Invalid email address!");
		this.value = emailAddress;
	}

	protected EmailAddress() {
	}

	/**
	 * Returns whether the given {@link String} is a valid {@link EmailAddress}
	 * which means you can safely instantiate the class.
	 * 
	 * @param candidate
	 */
	public static boolean isValid(String candidate) {
		return candidate == null ? false : PATTERN.matcher(candidate).matches();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EmailAddress)) {
			return false;
		}

		EmailAddress that = (EmailAddress) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "EmailAddress [value=" + value + "]";
	}
}