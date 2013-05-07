package com.smoopay.sts.entity.common;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Index;
import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.client.ClientRoleEnum;
import com.smoopay.sts.entity.common.client.role.ClientRole;

@Embeddable
public class AuthData {

	@Index(name = "clientLoginIndex", columnNames = "login")
	private String login;

	private String password;

	private ClientRole role;

	/**
	 * Creates a new {@link AuthData} from the given string source.
	 * 
	 * @param login
	 *            must not be {@literal null} or empty.
	 * @param password
	 *            must not be {@literal null} or empty.
	 * @param clientRole
	 *            must not be {@literal null}.
	 */
	public AuthData(String login, String password, ClientRoleEnum clientRole) {
		super();
		Assert.hasText(login, "Invalid login!");
		Assert.hasText(password, "Invalid password!");
		Assert.notNull(clientRole, "Invalid clientRole!");
		this.login = login;
		this.password = password;
		this.role = new ClientRole(clientRole);
	}

	/**
	 * Creates a new {@link AuthData} from the given string source.
	 * 
	 * @param login
	 *            must not be {@literal null} or empty.
	 * @param password
	 *            must not be {@literal null} or empty.
	 */
	public AuthData(String login, String password) {
		super();
		Assert.hasText(login, "Invalid login!");
		Assert.hasText(password, "Invalid password!");
		this.login = login;
		this.password = password;
		this.role = new ClientRole(ClientRoleEnum.NORMAL);
	}

	protected AuthData() {
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

	public ClientRole getRole() {
		return role;
	}

	public void setRole(ClientRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "AuthData [login=" + login + ", password=" + password + ", role=" + role + "]";
	}
}