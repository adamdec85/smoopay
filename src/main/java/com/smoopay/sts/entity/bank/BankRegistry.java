package com.smoopay.sts.entity.bank;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.springframework.util.Assert;

import com.smoopay.sts.entity.base.AbstractEntity;

/**
 * A BankRegistry.
 * 
 * http://www.ekontobankowe.pl/identyfikacja-banku-po-numerze-konta
 * 
 * @author Adam Dec
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "com.smoopay.sts.entity.bank.BankRegistry")
@NamedQuery(name = "byBankId", query = "SELECT b FROM BankRegistry b WHERE b.bankId = :bankId", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") })
public class BankRegistry extends AbstractEntity {

	@Index(name = "bankIdIndex", columnNames = "bankId")
	private Long bankId;

	private String bankName;

	/**
	 * Creates a new {@link BankRegistry}.
	 * 
	 * @param bankId
	 *            must not be {@literal null}.
	 * @param bankName
	 *            must not be {@literal null} or empty.
	 */
	public BankRegistry(Long bankId, String bankName) {
		Assert.notNull(bankId);
		Assert.hasText(bankName);
		this.bankId = bankId;
		this.bankName = bankName;
	}

	protected BankRegistry() {
	}

	public Long getBankId() {
		return bankId;
	}

	public String getBankName() {
		return bankName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankRegistry other = (BankRegistry) obj;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		return true;
	}
}