package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.shared.Contact;
import com.hokee.shared.User;

public class ContactService implements IContactService {

	private static final Logger log = LoggerFactory.getLogger(ContactService.class);

	@Override
	public boolean doesContactExistForName(final User user, final String contactName) {
		log.info("doesContactExist user={}, contactName={}", user, contactName);
		return true;
	}

	@Override
	public boolean doesContactExistForUser(final User user, final User contact) {
		log.info("doesContactExist user={}, contact={}", user, contact);
		return false;
	}

	@Override
	public Contact getContact(final User user, final String contactName) {
		log.info("getContact contactName={}", contactName);
		return new Contact(new User(), new User(), "user");
	}

	@Override
	public void addContact(final User user, final User contact, final String name) {
		log.info("addContact user={}, contact={}, name={}", user, contact, name);

	}
}
