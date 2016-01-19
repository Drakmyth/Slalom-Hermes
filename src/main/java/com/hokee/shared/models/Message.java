package com.hokee.shared.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {

	private String _id;
	private String _sender;
	private String _recipient;
	private String _message;

	@Deprecated
	public Message() {

		_id = null;
		_sender = null;
		_recipient = null;
		_message = null;
	}

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

	@Deprecated
	public void setId(final String id) {

		_id = id;
	}

	@JsonProperty("sender")
	public String getSender() {

		return _sender;
	}

	@Deprecated
	public void setSender(final String sender) {

		_sender = sender;
	}

	@JsonProperty("recipient")
	public String getRecipient() {

		return _recipient;
	}

	@Deprecated
	public void setRecipient(final String recipient) {

		_recipient = recipient;
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
