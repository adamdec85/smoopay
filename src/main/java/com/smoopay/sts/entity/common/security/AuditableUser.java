package com.smoopay.sts.entity.common.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AuditableUser {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(name = "auditUserName")
	private String userName;

	@Column(name = "auditUserRole")
	private String userRole;

	public AuditableUser() {

	}

	public AuditableUser(String userName, String userRole) {
		super();
		this.userName = userName;
		this.userRole = userRole;
	}

	/**
	 * Returns the identifier of the entity.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
			return false;
		}
		AuditableUser that = (AuditableUser) obj;
		return this.id.equals(that.getId());
	}

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "AuditableUser [userName=" + userName + ", userRole=" + userRole + "]";
	}
}