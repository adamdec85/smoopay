package com.smoopay.sts.common.validator.currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.smoopay.sts.common.dto.CurrencyEnum;

public class CurrencyValidator implements ConstraintValidator<Currency, String> {

	@Override
	public void initialize(Currency constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO: in the future get it from static DB table
		if (value == null) {
			return false;
		}
		try {
			CurrencyEnum.valueOf(value);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}