package com.smoopay.sts.entity.common.account.status;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.account.AccountStatusEnum;

/**
 * An AcountStatus.
 * 
 * @author Adam Dec
 */
@Embeddable
public class AccountStatus {

	@Column(name = "accountStatus")
	private String value;

	/**
	 * Creates a new {@link AccountStatusEnum} from the given string source.
	 * 
	 * @param acountStatusEnum
	 *            must not be {@literal null}.
	 */
	public AccountStatus(AccountStatusEnum acountStatusEnum) {
		Assert.notNull(acountStatusEnum, "AccountStatusEnum must not be null!");
		this.value = acountStatusEnum.toString();
	}

	protected AccountStatus() {
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

		if (!(obj instanceof AccountStatus)) {
			return false;
		}

		AccountStatus that = (AccountStatus) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public AccountStatusEnum getEnumValue() {
		return AccountStatusEnum.valueOf(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}