package com.smoopay.sts.entity.common.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.ClientFinancialBalance;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.data.ClientAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;

/**
 * http://www.hostettler.net/blog/2012/03/22/one-to-one-relations-in-jpa-2-dot-0
 * /
 * 
 * A ClientAccount.
 * 
 * @author Adam Dec
 * 
 */
@Entity
@Table(indexes = { @Index(name = "clientVirtualAccNoIndex", columnNames = { "virtualAccNo" }),
		@Index(name = "clientAccStatusIndex", columnNames = { "accountStatus" }), @Index(name = "clientAccCurrencyIndex", columnNames = { "currency" }),
		@Index(name = "clientAccOpeningDateIndex", columnNames = { "openingDate" }) }, appliesTo = "ClientAccount")
public class ClientAccount extends BaseAccount {

	@ManyToOne
	private Client client;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "clientAccountData_id")
	@Fetch(FetchMode.JOIN)
	private ClientAccountData clientAccountData;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "clientFinancialBalance_id")
	private ClientFinancialBalance clientFinancialBalance;

	public ClientAccount(String virtualAccNo, AccountStatus accountStatus, Currency currency) {
		super(virtualAccNo, accountStatus, currency);
	}

	protected ClientAccount() {
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ClientAccountData getClientAccountData() {
		return clientAccountData;
	}

	public void setClientAccountData(ClientAccountData clientAccountData) {
		this.clientAccountData = clientAccountData;
	}

	public ClientFinancialBalance getClientFinancialBalance() {
		return clientFinancialBalance;
	}

	public void setClientFinancialBalance(ClientFinancialBalance clientFinancialBalance) {
		this.clientFinancialBalance = clientFinancialBalance;
	}
}