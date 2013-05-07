package com.smoopay.sts.common.converter.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.common.dto.account.AccountStatusEnum;
import com.smoopay.sts.entity.common.account.status.AccountStatus;

@Component
public class AccountStatusConverter implements Converter<String, AccountStatus> {

	public AccountStatus convert(String accountStatus) {
		return new AccountStatus(AccountStatusEnum.valueOf(accountStatus));
	}
}