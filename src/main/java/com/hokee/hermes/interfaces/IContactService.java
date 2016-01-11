package com.hokee.hermes.interfaces;

import com.hokee.shared.Contact;

public interface IContactService {
	boolean doesContactExist(String contactName);
	Contact getContact(String contactName);
}
