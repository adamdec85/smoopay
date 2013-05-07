package com.smoopay.sts.services.client.response;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class Client_1_0 {

	@NotNull(message = "ClientId can not be NULL")
	private Long clientId;

	@NotNull(message = "FirstName can not be NULL")
	private String firstName;

	@NotNull(message = "LastName can not be NULL")
	private String lastName;

	@NotNull(message = "Pesel can not be NULL")
	private Long pesel;

	public Client_1_0() {
	}

	public Client_1_0(Long clientId, String firstName, String lastName, Long pesel) {
		super();
		this.clientId = clientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.pesel = pesel;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPesel(Long pesel) {
		this.pesel = pesel;
	}

	public Long getPesel() {
		return pesel;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}