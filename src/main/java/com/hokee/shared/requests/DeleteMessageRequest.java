package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessageRequest {

	private String _userId;
	private String _messageId;

	@Deprecated
	public DeleteMessageRequest() {

		_userId = null;
		_messageId = null;
	}

	@JsonCreator
	public DeleteMessageRequest(@JsonProperty("userId") final String userId,
	                            @JsonProperty("messageId") final String messageId) {

		this._userId = userId;
		this._messageId = messageId;
	}

	@JsonProperty("userId")
	public String getUserId() {

		return _userId;
	}

	@Deprecated
	public void setUserId(final String userId) {

		this._userId = userId;
	}

	@JsonProperty("messageId")
	public String getMessageId() {

		return _messageId;
	}

	@Deprecated
	public void setMessageId(final String messageId) {

		this._messageId = messageId;
	}
}
