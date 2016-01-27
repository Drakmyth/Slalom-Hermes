package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.Message;

public class AddMessageRequest {

	private Message _message;

	@Deprecated
	public AddMessageRequest() {

		_message = null;
	}

	@JsonCreator
	public AddMessageRequest(@JsonProperty("message") final Message message) {

		this._message = message;
	}

	@JsonProperty("message")
	public Message getMessage() {

		return _message;
	}

	@Deprecated
	public void setMessage(final Message message) {

		this._message = message;
	}
}
