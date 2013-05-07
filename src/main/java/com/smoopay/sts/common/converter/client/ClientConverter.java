package com.smoopay.sts.common.converter.client;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.services.client.response.Client_1_0;

@Component
public class ClientConverter implements Converter<Client, Client_1_0> {

	public Client_1_0 convert(Client c) {
		return new Client_1_0(c.getId(), c.getFirstName(), c.getLastName(), c.getPesel());
	}
}