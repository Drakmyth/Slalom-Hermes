package com.hokee.hermes.intentHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.models.Contact;
import com.hokee.shared.results.AddMessageResult;
import com.hokee.shared.models.User;

public class SendMessageHandler extends AbstractMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	private static final String MESSAGE = "message";
	private static final String RECIPIENT = "friend";

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;
	private final User _user;

	public SendMessageHandler(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
		_user = _userService.getUser(_sessionService.getSession().getUser().getUserId());
	}

	@Override
	public SpeechletResponse handleIntent(final Intent intent) throws SpeechletException {

		log.info("handleIntent intent={}", intent);

		Slot recipientSlot = intent.getSlot(RECIPIENT);
		Slot messageSlot = intent.getSlot(MESSAGE);
		if (recipientSlot != null && recipientSlot.getValue() != null &&
				messageSlot != null && messageSlot.getValue() != null) {
			log.info("full intent found - recipient={}, message={}", recipientSlot.getValue(), messageSlot.getValue());

			final String recipientName = intent.getSlot(RECIPIENT).getValue();
			final Contact recipient = _contactService.getContact(_user, recipientName);
			final String message = intent.getSlot(MESSAGE).getValue();

			return getFinalSendMessageResponse(recipient, message);
		} else if (recipientSlot != null && recipientSlot.getValue() != null) {
			log.info("partial intent found - recipient={}", recipientSlot.getValue());

			return handleRecipientDialogRequest(intent);
		}
		/*	removed support for partial with message only and no recipient or message

		else if (messageSlot != null && messageSlot.getValue() != null) {
			log.info("partial intent found - message present");

			return handleMessageDialogRequest(intent);
		} else {
			//return handleNoSlotDialogRequest(intent, session);
		}
		*/

		// TODO clean up, remove exception and respond telling the user there was a problem
		throw new SpeechletException("Invalid Intent");
	}

	private SpeechletResponse handleMessageDialogRequest(final Intent intent) {

		final String message = intent.getSlot(MESSAGE).getValue();

		// if we have contact perform final request
		if (_sessionService.getSession().getAttributes().containsKey(RECIPIENT)) {
			final Contact contact = (Contact) _sessionService.getSession().getAttribute(RECIPIENT);

			return getFinalSendMessageResponse(contact, message);
		}

		// prompt for recipient
		_sessionService.getSession().setAttribute(MESSAGE, message);
		final String responseText = "Send to who?";
		final String repromptText = "Who do you want to send your message to?";

		return newAskResponse(responseText, repromptText);
	}

	private SpeechletResponse handleRecipientDialogRequest(final Intent intent) {

		final String recipientName = intent.getSlot(RECIPIENT).getValue();

		if (!_contactService.doesContactExistForName(_user, recipientName)) {
			final String response = "The contact " + recipientName + " does not exist. " +
					"Who would you like to send a message to?";

			return newAskResponse(response, response);
		}

		final Contact recipient = _contactService.getContact(_user, recipientName);

		// if we have message perform final request
		if (_sessionService.getSession().getAttributes().containsKey(MESSAGE)) {

			final String message = _sessionService.getSession().getAttribute(MESSAGE).toString();
			return getFinalSendMessageResponse(recipient, message);
		}

		// prompt for message
		_sessionService.getSession().setAttribute(RECIPIENT, recipient);
		final String responseText = "What message?";
		final String repromptText = "What message would you like to send " + recipient.getName();

		return newAskResponse(responseText, repromptText);
	}

	private SpeechletResponse getFinalSendMessageResponse(final Contact recipient, final String message) {

		final AddMessageResult result =_messageService.sendMessage(recipient, message);

		final SimpleCard card = new SimpleCard();
		final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		if (result.isSuccess()) {
			card.setTitle("Message to " + recipient.getName());
			card.setContent(message);

			speech.setText("Message sent");
		} else {
			card.setTitle("Failed to send message to " + recipient.getName());
			card.setContent(result.getMessage());

			speech.setText("Failed to send message");
		}

		return SpeechletResponse.newTellResponse(speech, card);
	}

}
