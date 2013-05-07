package com.smoopay.sts.entity.client;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Index;
import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.client.ClientStatusEnum;
import com.smoopay.sts.entity.base.AbstractEntity;
import com.smoopay.sts.entity.common.AuthData;
import com.smoopay.sts.entity.common.EmailAddress;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.address.ClientAddress;
import com.smoopay.sts.entity.common.client.status.ClientStatus;
import com.smoopay.sts.entity.payments.ClientPayment;

/**
 * A smoopay client.
 * 
 * @author Adam Dec
 */
@Entity
public class Client extends AbstractEntity {

	@Index(name = "clientFirstNameIndex", columnNames = "firstName")
	private String firstName;

	@Index(name = "clientLastNameIndex", columnNames = "lastName")
	private String lastName;

	@Index(name = "clientPeselIndex", columnNames = "pesel")
	private Long pesel;

	@Column(unique = true)
	private AuthData authdata;

	private ClientStatus clientStatus;

	@Column(unique = true)
	private EmailAddress emailAddress;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Set<ClientAccount> clientAccounts = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id")
	private Set<ClientAddress> addresses = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "client_id")
	private Set<ClientPayment> payments = new HashSet<>();

	/**
	 * Creates a new {@link Client} from the given firstName and lastName.
	 * 
	 * @param firstName
	 *            must not be {@literal null} or empty.
	 * @param lastName
	 *            must not be {@literal null} or empty.
	 * @param pesel
	 *            must not greater than 0.
	 * @param authdata
	 *            must not be {@literal null}.
	 */
	public Client(String firstName, String lastName, long pesel, AuthData authdata) {
		Assert.hasText(firstName);
		Assert.hasText(lastName);
		Assert.isTrue(pesel > 0);
		Assert.notNull(authdata);
		this.firstName = firstName;
		this.lastName = lastName;
		this.pesel = pesel;
		this.authdata = authdata;
		this.clientStatus = new ClientStatus(ClientStatusEnum.BLOCKED);
	}

	protected Client() {
	}

	/**
	 * Adds the given {@link ClientAddress} to the {@link Client}.
	 * 
	 * @param address
	 *            must not be {@literal null}.
	 */
	public void add(ClientAddress address) {
		Assert.notNull(address);
		this.addresses.add(address);
	}

	/**
	 * Adds the given {@link ClientAccount} to the {@link Client}.
	 * 
	 * @param clientAccount
	 *            must not be {@literal null}.
	 */
	public void add(ClientAccount clientAccount) {
		Assert.notNull(clientAccount);
		this.clientAccounts.add(clientAccount);
	}

	/**
	 * Adds the given {@link ClientPayment} to the {@link Client}.
	 * 
	 * @param clientPayment
	 *            must not be {@literal null}.
	 */
	public void add(ClientPayment clientPayment) {
		Assert.notNull(clientPayment);
		this.payments.add(clientPayment);
	}

	/**
	 * Returns the firstName of the {@link Client}.
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the lastName of the {@link Client}.
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the pesel of the {@link Client}.
	 * 
	 * @return pesel
	 */
	public Long getPesel() {
		return pesel;
	}

	/**
	 * Returns the {@link EmailAddress} of the {@link Client}.
	 * 
	 * @return emailAddress
	 */
	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the {@link Client}'s {@link EmailAddress}.
	 * 
	 * @param emailAddress
	 *            must not be {@literal null}.
	 */
	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
	}

	public AuthData getAuthdata() {
		return authdata;
	}

	public ClientStatus getClientStatus() {
		return clientStatus;
	}

	public void setClientStatus(ClientStatus clientStatus) {
		this.clientStatus = clientStatus;
	}

	/**
	 * Return the {@link Client}'s addresses copy.
	 * 
	 * @return addresses
	 */
	public Set<ClientAddress> getAddressesSafe() {
		return Collections.unmodifiableSet(addresses);
	}

	/**
	 * Return the {@link Client}'s addresses.
	 * 
	 * @return addresses
	 */
	public Set<ClientAddress> getAddresses() {
		return addresses;
	}

	/**
	 * Return the {@link Client}'s clientAccounts copy.
	 * 
	 * @return clientAccounts
	 */
	public Set<ClientAccount> getClientAccountsSafe() {
		return Collections.unmodifiableSet(clientAccounts);
	}

	/**
	 * Return the {@link Client}'s clientAccounts.
	 * 
	 * @return clientAccounts
	 */
	public Set<ClientAccount> getClientAccounts() {
		return clientAccounts;
	}

	/**
	 * Return the {@link Client}'s payments.
	 * 
	 * @return payments
	 */
	public Set<ClientPayment> getClientPaymentsSafe() {
		return Collections.unmodifiableSet(payments);
	}

	public Set<ClientPayment> getClientPayments() {
		return payments;
	}

	/**
	 * Removes the {@link Client}'s addresses.
	 */
	public void removeAddresses() {
		addresses.clear();
	}

	/**
	 * Removes the {@link Client}'s payments.
	 */
	public void removePayments() {
		payments.clear();
	}

	/**
	 * Removes the {@link Client}'s clientAccounts.
	 */
	public void removeClientAccounts() {
		clientAccounts.clear();
	}
}