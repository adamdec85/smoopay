package com.smoopay.sts.entity.common.client.role;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.client.ClientRoleEnum;

/**
 * An ClientRole.
 * 
 * @author Adam Dec
 */
@Embeddable
public class ClientRole {

	@Column(name = "role")
	private String value;

	/**
	 * Creates a new {@link ClientRoleEnum} from the given string source.
	 * 
	 * @param clientRoleEnum
	 *            must not be {@literal null}.
	 */
	public ClientRole(ClientRoleEnum clientRoleEnum) {
		Assert.notNull(clientRoleEnum, "ClientRoleEnum must not be null!");
		this.value = clientRoleEnum.toString();
	}

	protected ClientRole() {
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

		if (!(obj instanceof ClientRole)) {
			return false;
		}

		ClientRole that = (ClientRole) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public ClientRoleEnum getEnumValue() {
		return ClientRoleEnum.valueOf(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}