package com.hokee.hermes.interfaces;

import com.hokee.hermes.models.Contact;
import com.hokee.hermes.models.User;
import com.hokee.shared.SendMessageOutput;

public interface IMessageService {
	SendMessageOutput sendMessage(final User sender, final Contact recipient, String message);
}
