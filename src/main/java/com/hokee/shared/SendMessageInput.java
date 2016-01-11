package com.hokee.shared;

public class SendMessageInput {

	private String _sender;
	private String _recipient;
	private String _message;

	public SendMessageInput() {
	}

	public SendMessageInput(final String sender,
	                        final String recipient,
	                        final String message) {

		_sender = sender;
		_recipient = recipient;
		_message = message;
	}

	public String getSender() {

		return _sender;
	}

	public String getRecipient() {

		return _recipient;
	}

	public String getMessage() {

		return _message;
	}

	public void setSender(final String _sender) {
		this._sender = _sender;
	}

	public void setRecipient(final String _recipient) {
		this._recipient = _recipient;
	}

	public void setMessage(final String _message) {
		this._message = _message;
	}
}
