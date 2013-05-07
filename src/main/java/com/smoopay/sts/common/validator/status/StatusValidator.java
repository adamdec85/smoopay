package com.smoopay.sts.common.validator.status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.smoopay.sts.common.dto.account.AccountStatusEnum;

public class StatusValidator implements ConstraintValidator<Status, String> {

	@Override
	public void initialize(Status constraintAnnotation) {
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO: in the future get it from static DB table
		if (value == null) {
			return false;
		}
		try {
			AccountStatusEnum.valueOf(value);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}