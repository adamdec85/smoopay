package com.smoopay.sts.entity.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.CurrencyEnum;

/**
 * An Currency.
 * 
 * @author Adam Dec
 */
@Embeddable
public class Currency {

	@Column(name = "currency")
	private String value;

	/**
	 * Creates a new {@link CurrencyEnum} from the given string source.
	 * 
	 * @param paymentStatusEnum
	 *            must not be {@literal null}.
	 */
	public Currency(CurrencyEnum currencyEnum) {
		Assert.notNull(currencyEnum, "CurrencyEnum must not be null!");
		this.value = currencyEnum.toString();
	}

	protected Currency() {
	}

	@Override
	public String toString() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Currency)) {
			return false;
		}

		Currency that = (Currency) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public String getValue() {
		return value;
	}

	public CurrencyEnum getEnumValue() {
		return CurrencyEnum.valueOf(value);
	}

	public void setValue(String value) {
		this.value = value;
	}
}