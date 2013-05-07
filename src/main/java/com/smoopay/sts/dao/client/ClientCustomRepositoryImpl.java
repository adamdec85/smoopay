package com.smoopay.sts.dao.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mysema.query.types.expr.BooleanExpression;
import com.smoopay.sts.entity.client.Client;
import com.smoopay.sts.entity.client.QClient;
import com.smoopay.sts.entity.common.Currency;
import com.smoopay.sts.entity.common.account.ClientAccount;
import com.smoopay.sts.entity.common.account.QClientAccount;
import com.smoopay.sts.entity.common.account.status.AccountStatus;
import com.smoopay.sts.entity.payments.ClientPayment;
import com.smoopay.sts.entity.payments.QClientPayment;
import com.smoopay.sts.repository.client.ClientAccountRepository;
import com.smoopay.sts.repository.client.ClientPaymentRepository;
import com.smoopay.sts.repository.client.ClientRepository;
import com.smoopay.sts.services.account.client.request.NewClientAccountRequest_1_0;
import com.smoopay.sts.services.account.client.response.ClientAccount_1_0;
import com.smoopay.sts.services.account.client.response.NewClientAccountResponse_1_0;
import com.smoopay.sts.services.client.request.NewClientRequest_1_0;
import com.smoopay.sts.services.client.response.NewClientResponse_1_0;
import com.smoopay.sts.services.payment.client.response.PaymentResponse_1_0;
import com.smoopay.sts.utils.CollectionUtils;

@Component
@Transactional
public class ClientCustomRepositoryImpl implements ClientCustomRepository {

	@Autowired
	private ClientPaymentRepository clientPaymentRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private ClientAccountRepository clientAccountRepository;

	@Autowired
	private CollectionUtils collectionUtils;

	@Autowired
	private ConversionService conversionService;

	@Transactional(readOnly = true)
	@Override
	public List<PaymentResponse_1_0> findClientPaymentsFromDate(Long clientId, Date from) {
		QClientPayment payment = QClientPayment.clientPayment;
		BooleanExpression clientIdPredicate = payment.client.id.eq(clientId);
		BooleanExpression creationTimePredicate = payment.creationTime.after(from);
		Iterable<ClientPayment> clients = clientPaymentRepository.findAll(clientIdPredicate.and(creationTimePredicate), payment.creationTime.asc());
		List<ClientPayment> clientPaymentList = collectionUtils.copyIterator(clients.iterator());
		if (clientPaymentList.size() > 0) {
			List<PaymentResponse_1_0> result = new ArrayList<>(clientPaymentList.size());
			for (ClientPayment clientPayment : clientPaymentList) {
				result.add(conversionService.convert(clientPayment, PaymentResponse_1_0.class));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public NewClientResponse_1_0 createNewClient(NewClientRequest_1_0 request) {
		Client clientEntity = conversionService.convert(request, Client.class);
		clientRepository.save(clientEntity);
		return new NewClientResponse_1_0(clientEntity.getId());
	}

	@Override
	public NewClientAccountResponse_1_0 createNewClientAccount(NewClientAccountRequest_1_0 request) {
		ClientAccount savedClientAccount = clientAccountRepository.save(conversionService.convert(request, ClientAccount.class));
		QClient client = QClient.client;
		BooleanExpression clientIdPredicate = client.id.eq(request.getClientId());
		Client c = clientRepository.findOne(clientIdPredicate);
		c.add(savedClientAccount);
		clientRepository.save(c);
		return new NewClientAccountResponse_1_0(savedClientAccount.getId(), savedClientAccount.getVirtualAccNo());
	}

	@Transactional(readOnly = true)
	@Override
	public List<ClientAccount_1_0> findAllClientAccountsByClientId(Long clientId) {
		QClientAccount clientAccount = QClientAccount.clientAccount;
		BooleanExpression clientIdPredicate = clientAccount.client.id.eq(clientId);
		Iterable<ClientAccount> clientAccountIter = clientAccountRepository.findAll(clientIdPredicate);
		List<ClientAccount> clientAccountList = collectionUtils.copyIterator(clientAccountIter.iterator());
		if (clientAccountList.size() > 0) {
			List<ClientAccount_1_0> result = new ArrayList<>(clientAccountList.size());
			for (ClientAccount ca : clientAccountList) {
				result.add(conversionService.convert(ca, ClientAccount_1_0.class));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<ClientAccount_1_0> findClientAccountsByCurrency(Long clientId, String currency) {
		QClientAccount clientAccount = QClientAccount.clientAccount;
		BooleanExpression clientIdPredicate = clientAccount.client.id.eq(clientId);
		BooleanExpression currencyPredicate = clientAccount.currency.eq(conversionService.convert(currency, Currency.class));
		Iterable<ClientAccount> clientAccountIter = clientAccountRepository.findAll(clientIdPredicate.and(currencyPredicate));
		List<ClientAccount> clientAccountList = collectionUtils.copyIterator(clientAccountIter.iterator());
		if (clientAccountList.size() > 0) {
			List<ClientAccount_1_0> result = new ArrayList<>(clientAccountList.size());
			for (ClientAccount ca : clientAccountList) {
				result.add(conversionService.convert(ca, ClientAccount_1_0.class));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<ClientAccount_1_0> findClientAccountsByStatus(Long clientId, String status) {
		QClientAccount clientAccount = QClientAccount.clientAccount;
		BooleanExpression clientIdPredicate = clientAccount.client.id.eq(clientId);
		BooleanExpression accountStatusPredicate = clientAccount.accountStatus.eq(conversionService.convert(status, AccountStatus.class));
		Iterable<ClientAccount> clientAccountIter = clientAccountRepository.findAll(clientIdPredicate.and(accountStatusPredicate));
		List<ClientAccount> clientAccountList = collectionUtils.copyIterator(clientAccountIter.iterator());
		if (clientAccountList.size() > 0) {
			List<ClientAccount_1_0> result = new ArrayList<>(clientAccountList.size());
			for (ClientAccount ca : clientAccountList) {
				result.add(conversionService.convert(ca, ClientAccount_1_0.class));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public List<ClientAccount_1_0> findClientAccountsByVirtualAccNo(Long clientId, String virtualNumber) {
		QClientAccount clientAccount = QClientAccount.clientAccount;
		BooleanExpression clientIdPredicate = clientAccount.client.id.eq(clientId);
		BooleanExpression virtualAccNoPredicate = clientAccount.virtualAccNo.eq(virtualNumber);
		Iterable<ClientAccount> clientAccountIter = clientAccountRepository.findAll(clientIdPredicate.and(virtualAccNoPredicate));
		List<ClientAccount> clientAccountList = collectionUtils.copyIterator(clientAccountIter.iterator());
		if (clientAccountList.size() > 0) {
			List<ClientAccount_1_0> result = new ArrayList<>(clientAccountList.size());
			for (ClientAccount ca : clientAccountList) {
				result.add(conversionService.convert(ca, ClientAccount_1_0.class));
			}
			return result;
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public ClientAccount getClientAccountByVirtualAccNo(Long clientId, String virtualNumber) {
		QClientAccount clientAccount = QClientAccount.clientAccount;
		BooleanExpression clientIdPredicate = clientAccount.client.id.eq(clientId);
		BooleanExpression virtualAccNoPredicate = clientAccount.virtualAccNo.eq(virtualNumber);
		return clientAccountRepository.findOne(clientIdPredicate.and(virtualAccNoPredicate));
	}

	@Transactional(readOnly = true)
	@Override
	public Client findClientByLogin(String login) {
		QClient client = QClient.client;
		BooleanExpression clientLoginPredicate = client.authdata.login.eq(login);
		return clientRepository.findOne(clientLoginPredicate);
	}
}