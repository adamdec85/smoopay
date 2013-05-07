package com.smoopay.sts.common.converter.account.client;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.common.converter.common.CurrencyConverter;
import com.smoopay.sts.common.dto.account.AccountStatusEnum;
import com.smoopay.sts.entity.common.ClientFinancialBalance;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.data.ClientAccountData;
import com.smoopay.sts.entity.common.account.status.AccountStatus;
import com.smoopay.sts.services.account.client.request.NewClientAccountRequest_1_0;
import com.smoopay.sts.utils.IbanNumberCreator;
import com.smoopay.sts.utils.VirtualAccIdCreator;

@Component
public class NewClientAccountConverter implements Converter<NewClientAccountRequest_1_0, ClientAccount> {

	@Autowired
	private CurrencyConverter currencyConverter;

	@Autowired
	private VirtualAccIdCreator virtualAccIdCreator;

	@Autowired
	private IbanNumberCreator ibanNumberCreator;

	public ClientAccount convert(NewClientAccountRequest_1_0 request) {
		Currency currency = currencyConverter.convert(request.getCurrency());
		ClientAccount clientAccount = new ClientAccount(virtualAccIdCreator.create(request.getClientId(), request.getBankName()), new AccountStatus(
				AccountStatusEnum.ACTIVE), currency);
		ClientAccountData clientAccountData = new ClientAccountData(request.getBankName(), request.getAccNrbNumber(), ibanNumberCreator.create(
				currency.getEnumValue(), request.getAccNrbNumber()));
		clientAccount.setClientAccountData(clientAccountData);
		clientAccount.setClientFinancialBalance(new ClientFinancialBalance(BigDecimal.ZERO));
		return clientAccount;
	}
}