package com.hokee.hermes.interfaces;

import com.hokee.hermes.models.Contact;

public interface IContactService {
	boolean doesContactExist(String contactName);
	Contact getContact(String contactName);
}
