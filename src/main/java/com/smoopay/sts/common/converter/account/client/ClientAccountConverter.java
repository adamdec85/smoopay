package com.smoopay.sts.common.converter.account.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.services.account.client.response.ClientAccount_1_0;
import com.smoopay.sts.utils.DateUtils;

@Component
public class ClientAccountConverter implements Converter<ClientAccount, ClientAccount_1_0> {

	@Autowired
	private DateUtils dateUtils;

	public ClientAccount_1_0 convert(ClientAccount clientAccount) {
		return new ClientAccount_1_0(clientAccount.getVirtualAccNo(), clientAccount.getAccountStatus().getEnumValue(), clientAccount.getCurrency()
				.getEnumValue(), dateUtils.convert(clientAccount.getOpeningDate()), dateUtils.convert(clientAccount.getExpiryDate()), clientAccount
				.getClientAccountData().getCashAccNRB(), clientAccount.getClientAccountData().getCashAccIBAN(), clientAccount.getClientFinancialBalance()
				.getPrimaryAccountBalance(), clientAccount.getClientFinancialBalance().getLockedBalance());
	}
}