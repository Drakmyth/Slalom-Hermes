package com.hokee.hermes.services;

import com.hokee.hermes.config.ContactServiceConfig;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.AddContactRequest;
import com.hokee.shared.requests.GetContactRequest;
import com.hokee.shared.results.AddContactResult;
import com.hokee.shared.results.GetContactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

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

		if (getContact(user, contactName) != null) {
			return true;
		}

		return false;
	}

	@Override
	public Contact getContact(final User user, final String contactName) {

		log.info("getContact user={}, contactName={}", user.getId(), contactName);

		final HttpEntity<GetContactRequest> request = new HttpEntity<>(new GetContactRequest(user.getId(), contactName), new HttpHeaders());
		final GetContactResult result = _restTemplate.postForEntity(
				_config.getGetContactAPIEndpoint(), request, GetContactResult.class).getBody();

		if (result.isSuccess()) {
			log.info("getContact status: {} - {}", result.isSuccess(), result.getErrorMessage());

			return result.getContact();
		}

		log.info("getContact status: {} - {}", result.isSuccess(), result.getErrorMessage());
		return null;
	}

	@Override
	public boolean addContact(final User user, final User contact, final String name) {

		log.info("addContact user={}, contact={}, name={}", user.getId(), contact.getId(), name);

		final HttpEntity<AddContactRequest> request = new HttpEntity<>(new AddContactRequest(user.getId(), contact.getPin(), name), new HttpHeaders());
		final AddContactResult result = _restTemplate.postForEntity(
				_config.getAddContactAPIEndpoint(), request, AddContactResult.class).getBody();

		log.info("addContact status: {} - {}", result.isSuccess(), result.getErrorMessage());

		return result.isSuccess();
	}
}
