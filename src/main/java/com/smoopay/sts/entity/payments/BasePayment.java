package com.smoopay.sts.entity.payments;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smoopay.sts.entity.base.AbstractEntity;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.PaymentStatus;

@MappedSuperclass
public class BasePayment extends AbstractEntity {

	private PaymentStatus paymentStatus;

	private BigDecimal amount;

	private Currency currency;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;

	protected BasePayment() {
	}

	public BasePayment(BigDecimal amount, Currency currency) {
		super();
		this.amount = amount;
		this.currency = currency;
		this.creationTime = new Date();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Currency getCurrency() {
		return currency;
	}
}