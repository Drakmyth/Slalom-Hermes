package com.hokee.hermes.interfaces;

import java.util.List;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.Message;
import com.hokee.shared.results.AddMessageResult;
import com.hokee.shared.models.User;

public interface IMessageService {
	AddMessageResult sendMessage(final Contact contact, String message);
	List<Message> getMessages(final User user);
	boolean deleteMessage(final User user, final String messageId);
}
