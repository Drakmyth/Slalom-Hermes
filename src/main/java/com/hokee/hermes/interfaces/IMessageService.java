package com.hokee.hermes.interfaces;

import com.hokee.hermes.models.Contact;
import com.hokee.hermes.models.User;

public interface IMessageService {
	void sendMessage(final User sender, final Contact recipient, String message);
}
