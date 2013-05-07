package com.smoopay.sts.services.account.client;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.smoopay.sts.common.validator.currency.Currency;
import com.smoopay.sts.common.validator.status.Status;
import com.smoopay.sts.dao.client.ClientCustomRepository;
import com.smoopay.sts.services.account.client.request.NewClientAccountRequest_1_0;
import com.smoopay.sts.services.account.client.response.ClientAccountResponse_1_0;
import com.smoopay.sts.services.account.client.response.NewClientAccountResponse_1_0;

@Controller
@RequestMapping("/client/account/v1")
@Transactional
public class ClientAccountService {

	@Autowired
	private ClientCustomRepository clientCustomRepository;

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@RequestMapping(value = "/new", method = RequestMethod.POST, consumes = "application/json", produces = "application/json", headers = "Accept=application/json")
	public @ResponseBody
	NewClientAccountResponse_1_0 newClient(@RequestBody NewClientAccountRequest_1_0 request) {
		return clientCustomRepository.createNewClientAccount(request);
	}

	@Secured("ROLE_NORMAL")
	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "/get/{clientId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientAccountResponse_1_0 getClientAccounts(@NotNull @PathVariable Long clientId) {
		return new ClientAccountResponse_1_0(clientCustomRepository.findAllClientAccountsByClientId(clientId));
	}

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "/currency/{clientId}/{currency}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientAccountResponse_1_0 getClientAccountsByCurrency(@NotNull @Currency @PathVariable String currency, @NotNull @PathVariable Long clientId) {
		return new ClientAccountResponse_1_0(clientCustomRepository.findClientAccountsByCurrency(clientId, currency));
	}

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "/status/{clientId}/{status}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientAccountResponse_1_0 getClientAccountsByStatus(@NotNull @Status @PathVariable String status, @NotNull @PathVariable Long clientId) {
		return new ClientAccountResponse_1_0(clientCustomRepository.findClientAccountsByStatus(clientId, status));
	}

	@Loggable(LogLevel.DEBUG)
	@Transactional(readOnly = true)
	@RequestMapping(value = "/virtualNumber/{clientId}/{virtualNumber}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ClientAccountResponse_1_0 getClientAccountsByVirtualNumber(@NotNull @PathVariable String virtualNumber, @NotNull @PathVariable Long clientId) {
		return new ClientAccountResponse_1_0(clientCustomRepository.findClientAccountsByVirtualAccNo(clientId, virtualNumber));
	}
}