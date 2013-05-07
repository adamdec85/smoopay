package com.smoopay.sts.entity.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.util.Assert;

import com.smoopay.sts.common.dto.payment.PaymentStatusEnum;

/**
 * An PaymentStatus.
 * 
 * @author Adam Dec
 */
@Embeddable
public class PaymentStatus {

	@Column(name = "paymentStatus")
	private String value;

	/**
	 * Creates a new {@link PaymentStatusEnum} from the given string source.
	 * 
	 * @param paymentStatusEnum
	 *            must not be {@literal null}.
	 */
	public PaymentStatus(PaymentStatusEnum paymentStatus) {
		Assert.notNull(paymentStatus, "PaymentStatus must not be null!");
		this.value = paymentStatus.toString();
	}

	protected PaymentStatus() {
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PaymentStatus)) {
			return false;
		}

		PaymentStatus that = (PaymentStatus) obj;
		return this.value.equals(that.value);
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}

	public String getValue() {
		return value;
	}

	public PaymentStatusEnum getEnumValue() {
		return PaymentStatusEnum.valueOf(value);
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "PaymentStatus [value=" + value + "]";
	}
}