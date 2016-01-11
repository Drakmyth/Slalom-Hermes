package com.hokee.shared;

public class SendMessageResult {

	private boolean _success;
	private String _message;

	public SendMessageResult() {
	}

	private SendMessageResult(final boolean success, final String message) {

		_success = success;
		_message = message;
	}

	public static SendMessageResult Success() {

		return new SendMessageResult(true, "Success");
	}

	public static SendMessageResult Failure(final String message) {

		return new SendMessageResult(false, message);
	}

	public boolean isSuccess() {

		return _success;
	}

	public String getMessage() {

		return _message;
	}

	public void setSuccess(final boolean _success) {
		this._success = _success;
	}

	public void setMessage(final String _message) {
		this._message = _message;
	}
}
