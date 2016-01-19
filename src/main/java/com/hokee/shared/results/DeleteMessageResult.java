package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessageResult {

	private boolean _success;
	private String _errorMessage;

	@Deprecated
	public DeleteMessageResult() {

		_success = false;
		_errorMessage = null;
	}

	@JsonCreator
	private DeleteMessageResult(@JsonProperty("success") final boolean success,
	                            @JsonProperty("error") final String errorMessage) {

		_success = success;
		_errorMessage = errorMessage;
	}

	public static DeleteMessageResult Success() {

		return new DeleteMessageResult(true, "Success");
	}

	public static DeleteMessageResult Failure(final String errorMessage) {

		return new DeleteMessageResult(false, errorMessage);
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
