package com.smoopay.sts.services.account.client.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.services.rest.response.BaseResponse;
import com.smoopay.sts.services.rest.response.ResponseStatus;

@JsonAutoDetect
public class ClientAccountResponse_1_0 extends BaseResponse {

	@Valid
	private List<ClientAccount_1_0> clientAccountList;

	public ClientAccountResponse_1_0(ResponseStatus responseStatus, String reasonText) {
		super(responseStatus, reasonText);
		this.clientAccountList = Collections.emptyList();
	}

	public ClientAccountResponse_1_0() {
		this.clientAccountList = Collections.emptyList();
	}

	public ClientAccountResponse_1_0(List<ClientAccount_1_0> clientAccountList) {
		super();
		this.clientAccountList = clientAccountList;
	}

	public ClientAccountResponse_1_0(ClientAccount_1_0 clientAccount) {
		super();
		this.clientAccountList = new ArrayList<>(1);
		this.clientAccountList.add(clientAccount);
	}

	public List<ClientAccount_1_0> getClientAccountList() {
		return clientAccountList;
	}

	public void setClientAccountList(List<ClientAccount_1_0> clientAccountList) {
		this.clientAccountList = clientAccountList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}