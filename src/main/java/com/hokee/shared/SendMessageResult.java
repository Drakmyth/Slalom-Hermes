package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageResult {

	private boolean _success;
	private String _message;

	@Deprecated
	public SendMessageResult() {

		_success = false;
		_message = null;
	}

	@JsonCreator
	private SendMessageResult(@JsonProperty("success") final boolean success,
	                          @JsonProperty("message") final String message) {

		_success = success;
		_message = message;
	}

	public static SendMessageResult Success() {

		return new SendMessageResult(true, "Success");
	}

	public static SendMessageResult Failure(final String message) {

		return new SendMessageResult(false, message);
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
