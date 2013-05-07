package com.smoopay.sts.services.client.response;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.services.rest.response.BaseResponse;

@JsonAutoDetect
public class NewClientResponse_1_0 extends BaseResponse {

	@NotNull(message = "ClientId can not be NULL")
	private Long clientId;

	public NewClientResponse_1_0() {

	}

	public NewClientResponse_1_0(Long clientId) {
		super();
		this.clientId = clientId;
	}

	public Long getClientId() {
		return clientId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
