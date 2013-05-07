package com.smoopay.sts.utils;

import org.springframework.stereotype.Service;

import com.smoopay.sts.common.dto.CurrencyEnum;

@Service
public class IbanNumberCreator {

	public String create(CurrencyEnum currency, String nrb) {
		switch (currency) {
		case PLN:
			return "PL " + nrb;
		case CHF:
			return "CH " + nrb;
		case GBP:
			return "EN " + nrb;
		case USD:
			return "US " + nrb;
		default:
			return "NOT SUPORTED CURRENCY";
		}
	}
}
