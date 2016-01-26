package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetContactRequest {

	private String _userId;
	private String _contactName;

	@Deprecated
	public GetContactRequest() {

		_userId = null;
		_contactName = null;
	}

	@JsonCreator
	public GetContactRequest(@JsonProperty("userId") final String userId,
	                         @JsonProperty("contactName") final String contactName) {

		_userId = userId;
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

	@JsonProperty("contactName")
	public String getContactName() {

		return _contactName;
	}

	@Deprecated
	public void setContactName(final String contactName) {

		_contactName = contactName;
	}
}
