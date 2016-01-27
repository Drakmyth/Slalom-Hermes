package com.hokee.hermes.interfaces;

import com.hokee.shared.models.Contact;
import com.hokee.shared.models.Message;
import com.hokee.shared.models.User;

public interface IMessageService {
	boolean sendMessage(final Contact contact, String message);
	Iterable<Message> getMessages(final User user);
	boolean deleteMessage(final User user, final String messageId);
}
