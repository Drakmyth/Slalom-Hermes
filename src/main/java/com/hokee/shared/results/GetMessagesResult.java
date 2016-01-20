package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.Message;

import java.util.Collections;

public class GetMessagesResult {

	private boolean _success;
	private Iterable<Message> _messages;
	private String _errorMessage;

	@Deprecated
	public GetMessagesResult() {

		_success = false;
		_messages = null;
		_errorMessage = null;
	}

	@JsonCreator
	private GetMessagesResult(@JsonProperty("success") final boolean success,
	                          @JsonProperty("messages") final Iterable<Message> messages,
	                          @JsonProperty("errorMessage") final String errorMessage) {

		_success = success;
		_messages = messages;
		_errorMessage = errorMessage;
	}

	public static GetMessagesResult Success(final Iterable<Message> messages) {

		return new GetMessagesResult(true, messages, "Success");
	}

	public static GetMessagesResult Failure(final String errorMessage) {

		return new GetMessagesResult(false, Collections.emptyList(), errorMessage);
	}

	@JsonProperty("success")
	public boolean isSuccess() {

		return _success;
	}

	@Deprecated
	public void setSuccess(final boolean success) {

		_success = success;
	}

	@JsonProperty("messages")
	public Iterable<Message> getMessages() {

		return _messages;
	}

	@Deprecated
	public void setMessages(final Iterable<Message> messages) {

		_messages = messages;
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
