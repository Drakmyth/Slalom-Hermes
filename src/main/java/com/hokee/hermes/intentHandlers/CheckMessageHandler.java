package com.hokee.hermes.intentHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IIntentHandler;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;

public class CheckMessageHandler implements IIntentHandler {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageHandler.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;

	public CheckMessageHandler(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
	}

	@Override
	public SpeechletResponse handleIntent(final Intent intent) throws SpeechletException {

		log.info("handleIntent intent={}", intent);



		return null;
	}
}
