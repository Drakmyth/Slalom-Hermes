package com.hokee.hermes.interfaces;

import com.hokee.shared.Contact;
import com.hokee.shared.User;

public interface IContactService {
	boolean doesContactExistForName(final User user, final String contactName);
	boolean doesContactExistForUser(final User user, final User contact);
	Contact getContact(final User user, final String contactName);
	void addContact(final User user, final User contact, final String name);
}
