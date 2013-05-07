package com.smoopay.sts.services.client.request;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class NewClientRequest_1_0 {

	@NotNull(message = "FirstName can not be NULL")
	private String firstName;

	@NotNull(message = "LastName can not be NULL")
	private String lastName;

	@NotNull(message = "Pesel can not be NULL")
	private Long pesel;

	private String emailAddress;

	@NotNull(message = "Street can not be NULL")
	private String street;

	@NotNull(message = "City can not be NULL")
	private String city;

	@NotNull(message = "PostCode can not be NULL")
	private String postCode;

	@NotNull(message = "Country can not be NULL")
	private String country;

	private boolean resident;

	@NotNull(message = "Login can not be NULL")
	private String login;

	@NotNull(message = "Password can not be NULL")
	private String password;

	public NewClientRequest_1_0() {

	}

	public NewClientRequest_1_0(String firstName, String lastName, Long pesel, String login, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.pesel = pesel;
		this.login = login;
		this.password = password;
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

	public Long getPesel() {
		return pesel;
	}

	public void setPesel(Long pesel) {
		this.pesel = pesel;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isResident() {
		return resident;
	}

	public void setResident(boolean resident) {
		this.resident = resident;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}