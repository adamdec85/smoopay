package com.smoopay.sts.entity.payments;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.PaymentStatus;
import com.smoopay.sts.entity.merchant.Merchant;

/**
 * A Payment.
 * 
 * @author Adam Dec
 */
@Entity
@Table(indexes = { @Index(name = "merchantIdIndex", columnNames = { "merchant_id" }), @Index(name = "paymentStatusIndex", columnNames = { "paymentStatus" }),
		@Index(name = "posIdIndex", columnNames = { "posId" }) }, appliesTo = "Payment")
public class Payment extends BasePayment {

	private Long posId;

	@Temporal(TemporalType.DATE)
	private Date modifyTime;

	@ManyToOne
	private Merchant merchant;

	protected Payment() {
	}

	public Payment(Long posId, BigDecimal amount, CurrencyEnum currencyEnum) {
		super(amount, new Currency(currencyEnum));
		this.posId = posId;
		setPaymentStatus(new PaymentStatus(PaymentStatusEnum.PENDING));
	}

	public Payment(Long posId, PaymentStatus paymentStatus, BigDecimal amount, CurrencyEnum currencyEnum) {
		super(amount, new Currency(currencyEnum));
		this.posId = posId;
		setPaymentStatus(paymentStatus);
	}

	public Long getPosId() {
		return posId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
}