package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.User;

public class GetMessagesRequest {

	private User _user;

	@Deprecated
	public GetMessagesRequest() {

		_user = null;
	}

	@JsonCreator
	public GetMessagesRequest(@JsonProperty("user") final User user) {

		_user = user;
	}

	@JsonProperty("user")
	public User getUser() {

		return _user;
	}

	@Deprecated
	public void setUser(final User user) {

		_user = user;
	}
}
