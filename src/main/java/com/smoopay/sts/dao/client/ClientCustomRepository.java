package com.smoopay.sts.dao.client;

import java.util.Date;
import java.util.List;

import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.services.account.client.request.NewClientAccountRequest_1_0;
import com.smoopay.sts.services.account.client.response.ClientAccount_1_0;
import com.smoopay.sts.services.account.client.response.NewClientAccountResponse_1_0;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;
import com.smoopay.sts.services.client.response.NewClientResponse_1_0;
import com.smoopay.sts.services.payment.client.response.PaymentResponse_1_0;

public interface ClientCustomRepository {

	List<PaymentResponse_1_0> findClientPaymentsFromDate(Long clientId, Date from);

	NewClientResponse_1_0 createNewClient(NewClientRequest_1_0 request);

	List<ClientAccount_1_0> findAllClientAccountsByClientId(Long clientId);

	List<ClientAccount_1_0> findClientAccountsByCurrency(Long clientId, String currency);

	List<ClientAccount_1_0> findClientAccountsByStatus(Long clientId, String status);

	List<ClientAccount_1_0> findClientAccountsByVirtualAccNo(Long clientId, String virtualNumber);

	ClientAccount getClientAccountByVirtualAccNo(Long clientId, String virtualNumber);

	NewClientAccountResponse_1_0 createNewClientAccount(NewClientAccountRequest_1_0 request);

	Client findClientByLogin(String login);
}