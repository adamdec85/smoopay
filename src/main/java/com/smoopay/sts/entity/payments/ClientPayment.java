package com.smoopay.sts.entity.payments;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.PaymentStatus;

/**
 * A ClientPayment.
 * 
 * @author Adam Dec
 */
@Entity
@Table(indexes = { @Index(name = "clientIdIndex", columnNames = { "client_id" }), @Index(name = "clientPaymentStatusIndex", columnNames = { "paymentStatus" }) }, appliesTo = "ClientPayment")
public class ClientPayment extends BasePayment {

	@ManyToOne
	private Client client;

	protected ClientPayment() {
	}

	public ClientPayment(BigDecimal amount, CurrencyEnum currencyEnum) {
		super(amount, new Currency(currencyEnum));
		setPaymentStatus(new PaymentStatus(PaymentStatusEnum.PENDING));
	}

	public ClientPayment(PaymentStatus paymentStatus, BigDecimal amount, CurrencyEnum currencyEnum) {
		super(amount, new Currency(currencyEnum));
		setPaymentStatus(paymentStatus);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}