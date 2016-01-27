package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserForPinRequest {

	private String _pin;

	@JsonCreator
	private GetUserForPinRequest(@JsonProperty("pin") final String pin) {
		_pin = pin;
	}

	@JsonProperty("userId")
	public String getPin() {

		return _pin;
	}

	@Deprecated
	public void setPin(final String pin) {

		_pin = pin;
	}
}
