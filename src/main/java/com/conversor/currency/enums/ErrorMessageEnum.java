package com.conversor.currency.enums;

public enum ErrorMessageEnum {

	USER_NOT_FOUND("User not find for this operation", 404),
	CURRENCY_NOT_SUPPORTED("An informed currency are not supported", 404),
	UNEXPECTED_ERROR("An unexpected error occurred", 500);

	private ErrorMessageEnum(String errorMessage, int errorCode) {
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}

	private String errorMessage;
	private int errorCode;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
}
