package com.hokee.hermes.contexts.sendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.contexts.AbstractContext;
import com.hokee.shared.Contact;

public class SendMessageContext extends AbstractContext {
	private static final Logger log = LoggerFactory.getLogger(SendMessageContext.class);

	private SendMessageContextStage _stage;
	private Contact _contact;
	private String _message;

	public SendMessageContext() {
		_stage = SendMessageContextStage.GET_CONTACT;
		_contact = null;
		_message = null;
	}

	public SendMessageContextStage getStage() {
		return _stage;
	}

	public void setStage(final SendMessageContextStage stage) {
		_stage = stage;
	}

	public Contact getContact() {
		return _contact;
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public String getMessage() {
		return _message;
	}

	public void setMessage(final String message) {
		_message = message;
	}
}
