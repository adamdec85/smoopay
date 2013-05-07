package com.smoopay.sts.common.converter.client;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.AuthData;
import com.smoopay.sts.entity.common.EmailAddress;
import com.smoopay.sts.entity.common.address.ClientAddress;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;

@Component
public class NewClientConverter implements Converter<NewClientRequest_1_0, Client> {

	public Client convert(NewClientRequest_1_0 newClient) {
		AuthData authdata = new AuthData(newClient.getLogin(), newClient.getPassword());
		Client client = new Client(newClient.getFirstName(), newClient.getLastName(), newClient.getPesel(), authdata);
		client.setEmailAddress(new EmailAddress(newClient.getEmailAddress()));
		ClientAddress address = new ClientAddress(newClient.getStreet(), newClient.getCity(), newClient.getPostCode(), newClient.getCountry(), newClient.isResident());
		client.add(address);
		return client;
	}
}