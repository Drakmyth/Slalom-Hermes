package com.hokee.hermes.interfaces;

import com.amazon.speech.speechlet.User;
import com.hokee.hermes.models.Contact;

public interface IMessageService {
	void sendMessage(final User sender, final Contact recipient, String message);
}
