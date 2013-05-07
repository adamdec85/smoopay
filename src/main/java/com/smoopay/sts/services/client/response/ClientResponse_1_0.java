package com.smoopay.sts.services.client.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.smoopay.sts.services.rest.response.BaseResponse;
import com.smoopay.sts.services.rest.response.ResponseStatus;

public class ClientResponse_1_0 extends BaseResponse {

	@Valid
	private List<Client_1_0> clientList;

	public ClientResponse_1_0() {
		this.clientList = Collections.emptyList();
	}

	public ClientResponse_1_0(ResponseStatus responseStatus, String reasonText) {
		super(responseStatus, reasonText);
		this.clientList = Collections.emptyList();
	}

	public ClientResponse_1_0(List<Client_1_0> clientList) {
		super();
		this.clientList = clientList;
	}

	public ClientResponse_1_0(Client_1_0 client) {
		super();
		this.clientList = new ArrayList<>(1);
		this.clientList.add(client);
	}

	public List<Client_1_0> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client_1_0> clientList) {
		this.clientList = clientList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
