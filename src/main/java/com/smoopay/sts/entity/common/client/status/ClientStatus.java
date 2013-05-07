package com.smoopay.sts.entity.common.client.status;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.client.ClientStatusEnum;

/**
 * An ClientStatus.
 * 
 * @author Adam Dec
 */
@Embeddable
public class ClientStatus {

	@Column(name = "status")
	private String value;

	/**
	 * Creates a new {@link ClientStatusEnum} from the given string source.
	 * 
	 * @param clientStatusEnum
	 *            must not be {@literal null}.
	 */
	public ClientStatus(ClientStatusEnum clientStatusEnum) {
		Assert.notNull(clientStatusEnum, "ClientStatusEnum must not be null!");
		this.value = clientStatusEnum.toString();
	}

	protected ClientStatus() {
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

		if (!(obj instanceof ClientStatus)) {
			return false;
		}

		ClientStatus that = (ClientStatus) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public ClientStatusEnum getEnumValue() {
		return ClientStatusEnum.valueOf(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}