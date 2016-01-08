package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageInput {

	private final String _sender;
	private final String _recipient;
	private final String _message;

	@JsonCreator
	public SendMessageInput(@JsonProperty("sender") final String sender,
	                        @JsonProperty("recipient") final String recipient,
	                        @JsonProperty("message") final String message) {

		_sender = sender;
		_recipient = recipient;
		_message = message;
	}

	@JsonProperty("sender")
	public String getSender() {

		return _sender;
	}

	@JsonProperty("recipient")
	public String getRecipient() {

		return _recipient;
	}

	@JsonProperty("message")
	public String getMessage() {

		return _message;
	}
}
