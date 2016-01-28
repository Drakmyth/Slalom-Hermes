package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserRequest {

	private String _userId;
	private String __pin;

	@Deprecated
	public AddUserRequest() {

		_userId = null;
		__pin = null;
	}

	@JsonCreator
	public AddUserRequest(@JsonProperty("userId") final String userId,
	                      @JsonProperty("pin") final String pin) {

		_userId = userId;
		__pin = pin;
	}

	@JsonProperty("userId")
	public String getUserId() {

		return _userId;
	}

	@Deprecated
	public void setUserId(final String userId) {

		_userId = userId;
	}

	@JsonProperty("pin")
	public String getPin() {

		return __pin;
	}

	@Deprecated
	public void setPin(final String pin) {

		__pin = pin;
	}
}
