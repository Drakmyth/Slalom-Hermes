package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddMessageResult {

	private boolean _success;
	private String _message;

	@Deprecated
	public AddMessageResult() {

		_success = false;
		_message = null;
	}

	@JsonCreator
	private AddMessageResult(@JsonProperty("success") final boolean success,
							 @JsonProperty("message") final String message) {

		_success = success;
		_message = message;
	}

	public static AddMessageResult Success() {

		return new AddMessageResult(true, "Success");
	}

	public static AddMessageResult Failure(final String message) {

		return new AddMessageResult(false, message);
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
