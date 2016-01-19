package com.hokee.hermes.contexts.sendMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.hokee.hermes.HermesIntents;
import com.hokee.hermes.contexts.AbstractContextProcessor;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.AddMessageResult;
import com.hokee.shared.User;

public class SendMessageProcessor extends AbstractContextProcessor {
	private static final Logger log = LoggerFactory.getLogger(SendMessageProcessor.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;
	private final User _user;
	private final SendMessageContext _context;

	private static final String CONTACT_NAME = "name";
	private static final String MESSAGE = "message";

	public SendMessageProcessor(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
		_user = _userService.getUser(_sessionService.getSession().getUser().getUserId());
		_context = _sessionService.getContext();
	}

	@Override
	public SpeechletResponse handleRequest(final Intent intent) throws SpeechletException {

		switch (_context.getStage()) {
			case GET_CONTACT:
				return getGetContactResponse(intent);
			case GET_MESSAGE:
				return getGetMessageResponse(intent);
			case REPEAT_MESSAGE:
				return getRepeatMessageResponse(intent);
			case SEND_MESSAGE:
				return getSendMessageResponse(intent);
		}

		throw new SpeechletException("Problem processing send message request");
	}

	private SpeechletResponse getGetContactResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in getContact stage intent={}", intentName);

		if (HermesIntents.SendMessage.name().equals(intentName)) {

			// set next stage and save context
			log.info("setting next stage to GET_MESSAGE");
			_context.setStage(SendMessageContextStage.GET_MESSAGE);
			_sessionService.setContext(_context);

			final String responseText = "Who would you like to send a message to?";
			return newAskResponse(responseText, responseText);
		}

		throw new SpeechletException("Issue in get message stage");
	}

	private SpeechletResponse getGetMessageResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in getMessage stage intent={}", intentName);

		if (HermesIntents.ContactName.name().equals(intentName)) {

			final Slot contactNameSlot = intent.getSlot(CONTACT_NAME);
			if (contactNameSlot == null || contactNameSlot.getValue() == null) {
				throw new SpeechletException("Contact name missing");
			}

			final String contactName = contactNameSlot.getValue();
			if (!_contactService.doesContactExistForName(_user, contactName)) {
				// use does not have a contact with that name
				log.info("setting next stage to GET_CONTACT, contact does not exist");
				_context.setStage(SendMessageContextStage.GET_CONTACT);
				_sessionService.setContext(_context);

				final String responseText = "Contact with that name does not exist. Who would you like to send a message to?";
				final String repromptText = "Who would you like to send a message to?";
				return newAskResponse(responseText, repromptText);
			}

			log.info("setting next stage to REPEAT_MESSAGE");
			_context.setStage(SendMessageContextStage.REPEAT_MESSAGE);
			_context.setContact(_contactService.getContact(_user, contactName));
			_sessionService.setContext(_context);

			final String responseText = "What message would you like to send?";
			return newAskResponse(responseText, responseText);
		}

		throw new SpeechletException("Issue in get message stage");
	}

	private SpeechletResponse getRepeatMessageResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in repeatMessage stage intent={}", intentName);

		if (HermesIntents.Message.name().equals(intentName)) {

			final Slot messageSlot = intent.getSlot(MESSAGE);
			if (messageSlot == null || messageSlot.getValue() == null) {
				throw new SpeechletException("Message missing");
			}

			log.info("setting next stage to SEND_MESSAGE");
			_context.setStage(SendMessageContextStage.SEND_MESSAGE);
			_context.setMessage(messageSlot.getValue());
			_sessionService.setContext(_context);
		}

		throw new SpeechletException("Issue in repeat message stage");
	}

	private SpeechletResponse getSendMessageResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in sendMessage stage intent={}", intentName);

		if (HermesIntents.ConfirmYes.name().equals(intentName)) {

			final AddMessageResult result =_messageService.sendMessage(_context.getContact(), _context.getMessage());
			final String responseText = "I'm sending your message now.";

			return newTellResponse(_context.getMessage() + " " + responseText);

		} else if (HermesIntents.ConfirmNo.name().equals(intentName)) {

			final AddMessageResult result =_messageService.sendMessage(_context.getContact(), _context.getMessage());
			final String responseText = "I'm sending your message now.";

			return newTellResponse(responseText);
		}

		throw new SpeechletException("Issue in send message stage");
	}
}
