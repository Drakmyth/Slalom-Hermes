package com.hokee.shared;

public class SendMessageOutput {

	private boolean _success;
	private String _message;

	public SendMessageOutput() {
	}

	private SendMessageOutput(final boolean success, final String message) {

		_success = success;
		_message = message;
	}

	public static SendMessageOutput Success() {

		return new SendMessageOutput(true, "Success");
	}

	public static SendMessageOutput Failure(final String message) {

		return new SendMessageOutput(false, message);
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
