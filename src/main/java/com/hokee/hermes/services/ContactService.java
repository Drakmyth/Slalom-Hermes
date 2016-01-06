package com.hokee.hermes.services;

import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.models.Contact;

public class ContactService implements IContactService {
	@Override
	public boolean doesContactExist(final String contactName) {
		return true;
	}

	@Override
	public Contact getContact(final String contactName) {
		final Contact contact = new Contact();
		contact.setId("0001");

		return contact;
	}
}
