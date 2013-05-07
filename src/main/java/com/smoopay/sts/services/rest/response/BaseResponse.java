package com.smoopay.sts.services.rest.response;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import com.smoopay.sts.services.rest.response.error.FieldValidationError;

@JsonAutoDetect
public class BaseResponse {

	private ResponseStatus responseStatus;
	private String reasonText;
	private List<FieldValidationError> fieldErrors = new ArrayList<FieldValidationError>(1);

	protected BaseResponse() {
		this.responseStatus = ResponseStatus.OK;
		this.reasonText = "";
	}

	public BaseResponse(String reasonText) {
		super();
		this.reasonText = reasonText;
	}

	public BaseResponse(ResponseStatus responseStatus, String reasonText) {
		super();
		this.responseStatus = responseStatus;
		this.reasonText = reasonText;
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getReasonText() {
		return reasonText;
	}

	public void setReasonText(String reasonText) {
		this.reasonText = reasonText;
	}

	public void addFieldError(String path, String message) {
		FieldValidationError fieldError = new FieldValidationError(path, message);
		fieldErrors.add(fieldError);
	}

	public void addFieldError(FieldValidationError fieldError) {
		fieldErrors.add(fieldError);
	}

	public List<FieldValidationError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldValidationError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}