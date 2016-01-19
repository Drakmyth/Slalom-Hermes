package com.hokee.shared.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact {

	private User _user;
	private User _contact;
	private String _name;

	@Deprecated
	public Contact() {

		_user = null;
		_contact = null;
		_name = null;
	}

	@JsonCreator
	public Contact(@JsonProperty("user") final User user,
	               @JsonProperty("contact") final User contact,
	               @JsonProperty("name") final String name) {

		this._user = user;
		this._contact = contact;
		this._name = name;
	}

	@JsonProperty("user")
	public User getUser() {

		return _user;
	}

	@Deprecated
	public void setUser(final User user) {

		this._user = user;
	}

	@JsonProperty("contact")
	public User getContact() {

		return _contact;
	}

	@Deprecated
	public void setContact(final User contact) {

		this._contact = contact;
	}

	@JsonProperty("name")
	public String getName() {

		return _name;
	}

	@Deprecated
	public void setName(final String name) {

		this._name = name;
	}
}
