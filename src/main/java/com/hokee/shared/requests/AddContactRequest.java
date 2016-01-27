package com.hokee.shared.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.Contact;

public class AddContactRequest {

	private Contact _contact;

	@JsonCreator
	public AddContactRequest(@JsonProperty("contact") final Contact contact) {
		_contact = contact;
	}

	@JsonProperty("contact")
	public Contact getContact() {

		return _contact;
	}

	@Deprecated
	public void setContact(final Contact contact) {

		_contact = contact;
	}
}
