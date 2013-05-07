package com.smoopay.sts.common.converter.common;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.common.dto.CurrencyEnum;
import com.smoopay.sts.entity.common.Currency;

@Component
public class CurrencyConverter implements Converter<String, Currency> {

	public Currency convert(String currency) {
		return new Currency(CurrencyEnum.valueOf(currency));
	}
}