package com.smoopay.sts.services.rest.response.error;

public class FieldValidationError {

	private String path;
	private String message;

	public FieldValidationError(String path, String message) {
		super();
		this.path = path;
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
