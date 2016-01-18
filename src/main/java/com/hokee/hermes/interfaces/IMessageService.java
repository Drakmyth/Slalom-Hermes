package com.hokee.hermes.interfaces;

import java.util.List;
import com.hokee.shared.Contact;
import com.hokee.shared.Message;
import com.hokee.shared.AddMessageResult;
import com.hokee.shared.User;

public interface IMessageService {
	AddMessageResult sendMessage(final Contact contact, String message);
	List<Message> getMessages(final User user);
	boolean deleteMessage(final User user, final String messageId);
}
