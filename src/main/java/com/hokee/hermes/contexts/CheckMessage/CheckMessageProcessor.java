package com.hokee.hermes.contexts.checkMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.hokee.hermes.contexts.AbstractContextProcessor;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.User;

public class CheckMessageProcessor extends AbstractContextProcessor {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageProcessor.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;
	private final User _user;
	private final CheckMessageContext _context;

	public CheckMessageProcessor(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
		_user = _userService.getUser(_sessionService.getSession().getUser().getUserId());
		_context = _sessionService.getContext();
	}

	@Override
	public SpeechletResponse handleRequest(final Intent intent) throws SpeechletException {

		log.info("context at stage={}", _context.getStage());



		return null;
	}
}
