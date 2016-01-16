package com.hokee.hermes.intentHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.hokee.hermes.contexts.Context;
import com.hokee.hermes.contexts.addContact.AddContactContext;
import com.hokee.hermes.contexts.addContact.AddContactContextStage;
import com.hokee.hermes.contexts.addContact.AddContactContextWrapper;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;

public class AddContactHandler extends AbstractMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageHandler.class);

	private static final String PIN = "pin";
	private static final String CONFIRM_PIN = "confirm_pin";
	private static final String NAME = "name";

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;

	public AddContactHandler(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
	}

	@Override
	public SpeechletResponse handleIntent(final Intent intent) throws SpeechletException {

		log.info("handlIntent intent={}", intent);

		final Slot pinSlot = intent.getSlot(PIN);
		final Slot nameSlot = intent.getSlot(NAME);

		// ensure we don't have an action without a context
		if (_sessionService.currentContext() == null && pinSlot.getValue() != null && nameSlot.getValue() != null) {
			throw new SpeechletException("Intent started with no context by action word");
		}

		// check to see if the context is missing
		if (_sessionService.currentContext() == null) {
			log.info("no context found, starting new context ");
			final AddContactContext context = new AddContactContext();
			context.setStage(AddContactContextStage.GET_PIN);
			_sessionService.setContext(context);
			_sessionService.setCurrentContext(Context.AddContact);
		}

		final AddContactContext c = _sessionService.getContext();
		final AddContactContextWrapper context = new AddContactContextWrapper(c);

		switch (context.getStage()) {
			case GET_PIN:
				break;
			case CONFIRM_PIN:

				if (pinSlot == null || pinSlot.getValue() == null) {
					throw new SpeechletException("Pin missing");
				}

				break;
			case GET_NAME:

				if (pinSlot == null || pinSlot.getValue() == null) {
					throw new SpeechletException("Pin missing");
				}

				break;
			case SAVE_CONTACT:

				if (nameSlot == null || nameSlot.getValue() == null) {
					throw new SpeechletException("Name missing");
				}
				break;
		}

		return null;
	}
}
