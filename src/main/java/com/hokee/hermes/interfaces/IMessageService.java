package com.hokee.hermes.interfaces;

import com.hokee.shared.Contact;
import com.hokee.shared.GetMessageOutput;
import com.hokee.shared.SendMessageResult;
import com.hokee.shared.User;

public interface IMessageService {
	SendMessageResult sendMessage(final User sender, final Contact recipient, String message);
	GetMessageOutput getMessage(final User user);
}
