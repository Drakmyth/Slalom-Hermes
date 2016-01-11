package com.hokee.hermes.interfaces;

import com.hokee.shared.Contact;
import com.hokee.shared.Message;
import com.hokee.shared.SendMessageResult;
import com.hokee.shared.User;

public interface IMessageService {
	SendMessageResult sendMessage(final User sender, final Contact recipient, String message);
	Iterable<Message> getMessages(final User user);
}
