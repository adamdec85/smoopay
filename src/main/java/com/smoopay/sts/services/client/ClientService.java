package com.smoopay.sts.services.client;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smoopay.sts.aop.logging.LogLevel;
import com.smoopay.sts.aop.logging.Loggable;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.repository.client.ClientRepository;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;
import com.smoopay.sts.services.client.response.ClientResponse_1_0;
import com.smoopay.sts.services.client.response.Client_1_0;
import com.smoopay.sts.services.client.response.NewClientResponse_1_0;
import com.smoopay.sts.services.rest.response.ResponseStatus;

@Controller
@RequestMapping("/client/v1")
@Transactional
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Autowired
	private ConversionService conversionService;

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "firstName/{firstName}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientResponse_1_0 getClientsByFirstName(@NotNull @PathVariable String firstName) {
		List<Client> clientList = clientRepository.findByFirstName(firstName);
		if (clientList != null && clientList.size() > 0) {
			List<Client_1_0> result = new ArrayList<>(clientList.size());
			for (Client c : clientList) {
				result.add(conversionService.convert(c, Client_1_0.class));
			}
			return new ClientResponse_1_0(result);
		}
		return new ClientResponse_1_0();
	}

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "lastName/{lastName}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientResponse_1_0 getClientsByLastName(@NotNull @PathVariable String lastName) {
		List<Client> clientList = clientRepository.findByLastName(lastName);
		if (clientList != null && clientList.size() > 0) {
			List<Client_1_0> result = new ArrayList<>(clientList.size());
			for (Client c : clientList) {
				result.add(conversionService.convert(c, Client_1_0.class));
			}
			return new ClientResponse_1_0(result);
		}
		return new ClientResponse_1_0();
	}

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "login/{login}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientResponse_1_0 getClientByLogin(@NotNull @PathVariable String login) {
		Client client = clientCustomRepository.findClientByLogin(login);
		if (client != null) {
			return new ClientResponse_1_0(conversionService.convert(client, Client_1_0.class));
		} else {
			return new ClientResponse_1_0(ResponseStatus.ERROR, "Client with login=" + login + " has not been found.");
		}
	}

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "id/{clientId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientResponse_1_0 getClientById(@NotNull @PathVariable Long clientId) {
		Client client = clientRepository.findOne(clientId);
		if (client != null) {
			return new ClientResponse_1_0(conversionService.convert(client, Client_1_0.class));
		} else {
			return new ClientResponse_1_0(ResponseStatus.ERROR, "Client with id=" + clientId + " has not been found.");
		}
	}

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	NewClientResponse_1_0 newClient(@RequestBody NewClientRequest_1_0 request) {
		return clientCustomRepository.createNewClient(request);
	}
}