package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserResult {

	private boolean _success;
	private String _errorMessage;

	@Deprecated
	public AddUserResult() {

		_success = false;
		_errorMessage = null;
	}

	@JsonCreator
	private AddUserResult(@JsonProperty("success") final boolean success,
	                      @JsonProperty("errorMessage") final String errorMessage) {

		_success = success;
		_errorMessage = errorMessage;
	}

	public static AddUserResult Success() {

		return new AddUserResult(true, "Success");
	}

	public static AddUserResult Failure(final String errorMessage) {

		return new AddUserResult(false, errorMessage);
	}

	@JsonProperty("success")
	public boolean isSuccess() {

		return _success;
	}

	@Deprecated
	public void setSuccess(final boolean success) {

		_success = success;
	}

	@JsonProperty("errorMessage")
	public String getErrorMessage() {

		return _errorMessage;
	}

	@Deprecated
	public void setErrorMessage(final String errorMessage) {

		_errorMessage = errorMessage;
	}
}
