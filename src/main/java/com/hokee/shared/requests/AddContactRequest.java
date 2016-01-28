package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddContactRequest {

	private String _userId;
	private String _contactPin;
	private String _contactName;

	@Deprecated
	public AddContactRequest() {

		_userId = null;
		_contactPin = null;
		_contactName = null;
	}

	@JsonCreator
	public AddContactRequest(@JsonProperty("userId") final String userId,
	                         @JsonProperty("contactPin") final String contactPin,
	                         @JsonProperty("contactName") final String contactName) {

		_userId = userId;
		_contactPin = contactPin;
		_contactName = contactName;
	}

	@JsonProperty("userId")
	public String getUserId() {

		return _userId;
	}

	@Deprecated
	public void setUserId(final String userId) {

		_userId = userId;
	}

	@JsonProperty("contactPin")
	public String getContactPin() {

		return _contactPin;
	}

	@Deprecated
	public void setContactPin(final String contactPin) {

		_contactPin = contactPin;
	}

	@JsonProperty("contactName")
	public String getContactName() {

		return _contactName;
	}

	@Deprecated
	public void setContactName(final String contactName) {

		_contactName = contactName;
	}
}
