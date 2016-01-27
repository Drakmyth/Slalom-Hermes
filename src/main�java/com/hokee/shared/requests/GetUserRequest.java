package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUserRequest {

	private String _userId;

	@JsonCreator
	public GetUserRequest(@JsonProperty("userId") final String userId) {
		_userId = userId;
	}

	@JsonProperty("userId")
	public String getUserId() {

		return _userId;
	}

	@Deprecated
	public void setUserId(final String userId) {

		_userId = userId;
	}
}
