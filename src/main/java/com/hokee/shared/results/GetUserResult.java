package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.User;

public class GetUserResult {

	private boolean _success;
	private User _user;
	private String _errorMessage;

	@Deprecated
	public GetUserResult() {

		_success = false;
		_user = null;
		_errorMessage = null;
	}

	@JsonCreator
	private GetUserResult(@JsonProperty("success") final boolean success,
						  @JsonProperty("user") final User user,
						  @JsonProperty("errorMessage") final String errorMessage) {

		_success = success;
		_user = user;
		_errorMessage = errorMessage;
	}

	public static GetUserResult Success(final User user) {

		return new GetUserResult(true, user, "Success");
	}

	public static GetUserResult Failure(final String errorMessage) {

		return new GetUserResult(false, null, errorMessage);
	}

	@JsonProperty("success")
	public boolean isSuccess() {

		return _success;
	}

	@Deprecated
	public void setSuccess(final boolean success) {

		_success = success;
	}

	@JsonProperty("user")
	public User getUser() {

		return _user;
	}

	@Deprecated
	public void setUser(final User user) {

		_user = user;
	}

	@JsonProperty("errorMessage")
	public String getErrorMessage() {

		return _errorMessage;
	}

	@Deprecated
	public void setErrorMessage(final String errorMessage) {

		_errorMessage = errorMessage;
	}
}
