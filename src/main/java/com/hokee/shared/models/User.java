package com.hokee.shared.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private String _id;
	private String _pin;

	@Deprecated
	public User() {

		_id = null;
		_pin = null;
	}

	@JsonCreator
	public User(@JsonProperty("id") final String id,
	            @JsonProperty("pin") final String pin) {

		_id = id;
		_pin = pin;
	}

	@JsonProperty("id")
	public String getId() {

		return _id;
	}

	@Deprecated
	public void setId(final String id) {

		_id = id;
	}

	@JsonProperty("pin")
	public String getPin() {

		return _pin;
	}

	public void setPin(final String pin) {

		_pin = pin;
	}
}
