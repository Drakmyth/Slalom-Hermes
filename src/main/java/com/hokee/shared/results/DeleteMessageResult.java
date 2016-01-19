package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessageResult {

	private boolean _success;
	private String _message;

	@Deprecated
	public DeleteMessageResult() {

		_success = false;
		_message = null;
	}

	@JsonCreator
	private DeleteMessageResult(@JsonProperty("success") final boolean success,
	                            @JsonProperty("message") final String message) {

		_success = success;
		_message = message;
	}

	public static DeleteMessageResult Success() {

		return new DeleteMessageResult(true, "Success");
	}

	public static DeleteMessageResult Failure(final String message) {

		return new DeleteMessageResult(false, message);
	}

	@JsonProperty("success")
	public boolean isSuccess() {

		return _success;
	}

	@Deprecated
	public void setSuccess(final boolean success) {

		_success = success;
	}

	@JsonProperty("message")
	public String getMessage() {

		return _message;
	}

	@Deprecated
	public void setMessage(final String message) {

		_message = message;
	}
}
