package com.hokee.hermes.services;

import com.hokee.hermes.interfaces.IContactService;
import com.hokee.shared.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactService implements IContactService {

	private static final Logger log = LoggerFactory.getLogger(ContactService.class);

	@Override
	public boolean doesContactExist(final String contactName) {

		log.info("doesContactExist contactName={}", contactName);
		return true;
	}

	@Override
	public Contact getContact(final String contactName) {

		log.info("getContact contactName={}", contactName);

		final Contact contact = new Contact("0001", contactName);
		return contact;
	}
}
