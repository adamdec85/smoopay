package com.smoopay.sts.services.account.client.response;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.services.rest.response.BaseResponse;

@JsonAutoDetect
public class NewClientAccountResponse_1_0 extends BaseResponse {

	@NotNull(message = "ClientId can not be NULL")
	private Long clientAccountId;

	@NotNull(message = "VirtualAccNo can not be NULL")
	private String virtualAccNo;

	public NewClientAccountResponse_1_0() {

	}

	public NewClientAccountResponse_1_0(Long clientAccountId, String virtualAccNo) {
		super();
		this.clientAccountId = clientAccountId;
		this.virtualAccNo = virtualAccNo;
	}

	public Long getClientAccountId() {
		return clientAccountId;
	}

	public String getVirtualAccNo() {
		return virtualAccNo;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
