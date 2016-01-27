package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;
import com.hokee.hermes.config.ContactServiceConfig;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.User;

public class ContactService implements IContactService {

	private static final Logger log = LoggerFactory.getLogger(ContactService.class);

	private ContactServiceConfig _config;
	private RestTemplate _restTemplate;

	public ContactService(final ContactServiceConfig config, final RestTemplate restTemplate) {
		_config = config;
		_restTemplate = restTemplate;
	}

	// used to verify user has contact for given contact name before sending message
	@Override
	public boolean doesContactExistForName(final User user, final String contactName) {
		log.info("doesContactExist user={}, contactName={}", user, contactName);
		return true;
	}

	// used to verify contact doesn't exist for user before adding
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
		log.info("xaddContact user={}, contact={}, name={}", user, contact, name);

	}
}
