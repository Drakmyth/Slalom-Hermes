package com.hokee.hermes.contexts.addContact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddContactContextWrapper {
	private static final Logger log = LoggerFactory.getLogger(AddContactContextWrapper.class);

	private final AddContactContext _context;

	public AddContactContextWrapper(final AddContactContext context) {
		_context = context;
	}

	public AddContactContextStage getStage() {
		return _context.getStage();
	}

	public void setStage(AddContactContextStage stage) {
		_context.setStage(stage);
	}

	public AddContactContext getContext() {
		return _context;
	}
}
