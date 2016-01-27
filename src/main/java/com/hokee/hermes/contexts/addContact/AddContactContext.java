package com.hokee.hermes.contexts.addContact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.contexts.AbstractContext;

public class AddContactContext extends AbstractContext {
	private static final Logger log = LoggerFactory.getLogger(AddContactContext.class);

	private AddContactContextStage _stage;
	private String _pin;

	public AddContactContext() {
		_stage = AddContactContextStage.GET_PIN;
		_pin = null;
	}

	public AddContactContextStage getStage() {
		return _stage;
	}

	public void setStage(AddContactContextStage stage) {
		_stage = stage;
	}

	public String getPin() {
		return _pin;
	}

	public void setPin(String pin) {
		_pin = pin;
	}
}
