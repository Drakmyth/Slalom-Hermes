package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

	private final String _id;
	private final String _sender;
	private final String _recipient;
	private final String _message;

	@JsonCreator
	public Message(@JsonProperty("id") final String id,
	               @JsonProperty("sender") final String sender,
	               @JsonProperty("recipient") final String recipient,
	               @JsonProperty("message") final String message) {

		_id = id;
		_sender = sender;
		_recipient = recipient;
		_message = message;
	}

	@JsonProperty("id")
	public String getId() {

		return _id;
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
