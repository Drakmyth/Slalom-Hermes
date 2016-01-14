package com.hokee.hermes.interfaces;

import java.util.List;
import com.hokee.shared.Contact;
import com.hokee.shared.Message;
import com.hokee.shared.SendMessageResult;
import com.hokee.shared.User;

public interface IMessageService {
	SendMessageResult sendMessage(final User sender, final Contact recipient, String message);
	List<Message> getMessages(final User user);
	boolean deleteMessage(final String messageId);
}
