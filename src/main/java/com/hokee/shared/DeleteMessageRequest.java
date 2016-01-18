package com.hokee.shared;

public class DeleteMessageRequest {

	private String userId;
	private String messageId;

	public DeleteMessageRequest(final String userId, final String messageId) {
		this.userId = userId;
		this.messageId = messageId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(final String messageId) {
		this.messageId = messageId;
	}
}
