package com.hokee.hermes.contexts.addContact;

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
import com.hokee.shared.User;

public class AddContactProcessor extends AbstractContextProcessor {
	private static final Logger log = LoggerFactory.getLogger(AddContactProcessor.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;
	private final User _user;
	private final AddContactContext _context;

	private static final String PIN = "pin";
	private static final String NAME = "name";

	public AddContactProcessor(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
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

		// Stages: GET_PIN -> CONFIRM_PIN -> GET_NAME -> SAVE_CONTACT
		switch (_context.getStage()) {
			case GET_PIN:
				return getGetPinResponse(intent);
			case CONFIRM_PIN:
				return getConfirmPinResponse(intent);
			case GET_NAME:
				return getGetNameResponse(intent);
			case SAVE_CONTACT:
				return getSaveContactResponse(intent);
		}

		throw new SpeechletException("Problem processing add contact request");
	}

	private SpeechletResponse getGetPinResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in getPin stage intent={}", intentName);

		if (HermesIntents.AddContact.name().equals(intentName)) {

			// set next stage and save context
			log.info("setting next stage to CONFIRM_PIN");
			_context.setStage(AddContactContextStage.CONFIRM_PIN);
			_sessionService.setContext(_context);

			final String responseText = "What is the new contacts's 4 digit pin?";
			return newAskResponse(responseText, responseText);
		}

		throw new SpeechletException("Issue in get pin stage");
	}

	private SpeechletResponse getConfirmPinResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in confirmPin stage intent={}", intentName);

		if (HermesIntents.AddContact.name().equals(intentName)) {

			final Slot pinSlot = intent.getSlot(PIN);
			if (pinSlot == null || pinSlot.getValue() == null) {
				throw new SpeechletException("Pin missing");
			}

			final String pin = pinSlot.getValue();
			final User contact = _userService.getUserForPin(pin);

			if (contact == null) {
				// user does not exist, set to previous stage and save context
				log.info("setting next stage to GET_PIN, user does not exist");
				_context.setStage(AddContactContextStage.GET_PIN);
				_sessionService.setContext(_context);

				final String responseText = "User for pin does not exist. What is the new context's 4 digit pin?";
				final String repromptText = "What is the new context's 4 digit pin?";
				return newAskResponse(responseText, repromptText);
			}

			if (_contactService.doesContactExistForUser(_user, contact)) {
				// user already has for this name
				log.info("ending interaction, contact with the pin already exists");

				final String responseText = "User with that pin is already in your contact list.";
				return newTellResponse(responseText);
			}

			log.info("setting next stage to GET_NAME");
			_context.setStage(AddContactContextStage.GET_NAME);
			_context.setPin(pinSlot.getValue());
			_sessionService.setContext(_context);

			final String responseText = "The new contact's pin is " + pinSlot.getValue() + ". Is this correct?";
			return newAskResponse(responseText, responseText);
		}

		throw new SpeechletException("Issue in confirm pin stage");
	}

	private SpeechletResponse getGetNameResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in getName stage intent={}", intentName);

		if (HermesIntents.ConfirmYes.name().equals(intentName)) {

			log.info("setting next stage to SAVE_CONTACT");
			_context.setStage(AddContactContextStage.SAVE_CONTACT);
			_sessionService.setContext(_context);

			final String responseText = "What name would you like to call this contact?";
			return newAskResponse(responseText, responseText);

		} else if (HermesIntents.ConfirmNo.name().equals(intentName)) {

			log.info("setting next stage to CONFIRM_PIN since user did not confirm the pin");
			_context.setStage(AddContactContextStage.CONFIRM_PIN);
			_context.setPin(null);
			_sessionService.setContext(_context);

			final String responseText = "What is the new contacts's 4 digit pin?";
			return newAskResponse(responseText, responseText);
		}

		throw new SpeechletException("Issue in get name stage");
	}

	private SpeechletResponse getSaveContactResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in saveContact stage intent={}", intentName);

		if (HermesIntents.ContactName.name().equals(intentName)) {
			final Slot nameSlot = intent.getSlot(NAME);
			if (nameSlot == null || nameSlot.getValue() == null) {
				throw new SpeechletException("Name missing");
			}

			final User contact = _userService.getUserForPin(_context.getPin());
			_contactService.addContact(_user, contact, nameSlot.getValue());

			return newTellResponse("Adding new contact: " + nameSlot.getValue());
		}

		throw new SpeechletException("Issue in save contact stage");
	}
}
