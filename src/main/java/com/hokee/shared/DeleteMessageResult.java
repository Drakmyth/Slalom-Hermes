package com.hokee.shared;

public class DeleteMessageResult {

	private boolean _success;
	private String _message;

	public DeleteMessageResult() {
	}

	private DeleteMessageResult(final boolean success, final String message) {

		_success = success;
		_message = message;
	}

	public static DeleteMessageResult Success() {

		return new DeleteMessageResult(true, "Success");
	}

	public static DeleteMessageResult Failure(final String message) {

		return new DeleteMessageResult(false, message);
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
