package com.hokee.hermes.interfaces;

import com.hokee.shared.models.Contact;
import com.hokee.shared.models.User;

public interface IContactService {
	boolean doesContactExistForName(final User user, final String contactName);
	Contact getContact(final User user, final String contactName);
	boolean addContact(final User user, final User contact, final String name);
}
