package com.smoopay.sts.common.converter;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.stereotype.Component;

import com.smoopay.sts.common.converter.account.client.ClientAccountConverter;
import com.smoopay.sts.common.converter.account.client.NewClientAccountConverter;
import com.smoopay.sts.common.converter.client.ClientConverter;
import com.smoopay.sts.common.converter.client.NewClientConverter;
import com.smoopay.sts.common.converter.common.AccountStatusConverter;
import com.smoopay.sts.common.converter.common.CurrencyConverter;
import com.smoopay.sts.common.converter.payment.client.ClientPaymentConverter;

/**
 * http://static.springsource.org/autorepo/docs/spring/3.2.2.RELEASE/spring-
 * framework-reference/htmlsingle/#validation
 * 
 * @author Adam Dec
 * 
 */
@Component
public class ConversionServiceFactory extends FormattingConversionServiceFactoryBean {

	@Autowired
	private ClientPaymentConverter clientPaymentConverter;

	@Autowired
	private ClientConverter clientConverter;

	@Autowired
	private NewClientConverter newClientConverter;

	@Autowired
	private AccountStatusConverter accountStatusConverter;

	@Autowired
	private CurrencyConverter currencyConverter;

	@Autowired
	private ClientAccountConverter clientAccountConverter;

	@Autowired
	private NewClientAccountConverter newClientAccountConverter;

	@PostConstruct
	public void init() {
		@SuppressWarnings("rawtypes")
		Set<Converter> converters = new HashSet<>();
		converters.add(clientPaymentConverter);
		converters.add(clientConverter);
		converters.add(newClientConverter);
		converters.add(accountStatusConverter);
		converters.add(currencyConverter);
		converters.add(clientAccountConverter);
		converters.add(newClientAccountConverter);
		setConverters(converters);

		// TODO: Add field formatters
	}
}