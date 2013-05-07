package com.smoopay.sts.entity.common;

import javax.persistence.Embeddable;

import org.springframework.util.Assert;

/**
 * A value object abstraction of an KRS, REGON, NIP.
 * 
 * @author Adam Dec
 */
@Embeddable
public class CompanyRegistry {

	private String krs;

	private String regon;

	private String nip;

	/**
	 * Creates a new {@link CompanyRegistry} from the given string source.
	 * 
	 * @param krs
	 *            must not be {@literal null} or empty.
	 * @param regon
	 *            must not be {@literal null} or empty.
	 * @param nip
	 *            must not be {@literal null} or empty.
	 */
	public CompanyRegistry(String krs, String regon, String nip) {
		super();
		Assert.hasText(krs, "Invalid krs!");
		Assert.hasText(regon, "Invalid regon!");
		Assert.hasText(nip, "Invalid nip!");
		this.krs = krs;
		this.regon = regon;
		this.nip = nip;
	}

	protected CompanyRegistry() {
	}

	public String getKrs() {
		return krs;
	}

	public void setKrs(String krs) {
		this.krs = krs;
	}

	public String getRegon() {
		return regon;
	}

	public void setRegon(String regon) {
		this.regon = regon;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	@Override
	public String toString() {
		return "CompanyRegistry [krs=" + krs + ", regon=" + regon + ", nip=" + nip + "]";
	}
}