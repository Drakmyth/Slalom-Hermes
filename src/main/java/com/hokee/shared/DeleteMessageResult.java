package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessageResult {

	private final boolean _success;
	private final String _message;

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

	@JsonProperty("message")
	public String getMessage() {

		return _message;
	}
}
