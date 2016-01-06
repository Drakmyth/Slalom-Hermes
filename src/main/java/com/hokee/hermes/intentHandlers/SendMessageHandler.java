package com.hokee.hermes.intentHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.hermes.models.Contact;
import com.hokee.hermes.models.User;

public class SendMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	private static final String MESSAGE = "message";
	private static final String RECIPIENT = "friend";

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;

	public SendMessageHandler(final IMessageService messageService, final IContactService contactService, final IUserService userService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
	}

	public SpeechletResponse getSendMessageResponse(final Intent intent) throws SpeechletException {

		final User user = _userService.getUser();
		final String recipient = intent.getSlot(RECIPIENT).getValue();
		final String message = intent.getSlot(MESSAGE).getValue();

		if (recipient != null && message != null) {

			if (!_contactService.doesContactExist(recipient)) {
				return handleContactNotFound(recipient);
			}

			Contact contact = _contactService.getContact(recipient);

			return handleFullIntent(user, contact, message);
		} else if (recipient == null && message == null) {
			return handlePartialIntent();
		} else if (recipient != null && message == null) {

			if (!_contactService.doesContactExist(recipient)) {
				return handleContactNotFound(recipient);
			}

			Contact contact = _contactService.getContact(recipient);

			return handlePartialIntent(contact);
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}

	private SpeechletResponse handleContactNotFound(final String recipient) {

		SimpleCard card = new SimpleCard();
		card.setTitle("Hermes Error");
		card.setContent("Hermes could not find the following contact: " + recipient);

		final String response = "Hermes could not find " + recipient + " in your contacts";
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(response);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse handleFullIntent(final User sender, final Contact recipient, final String message) {

		_messageService.sendMessage(sender, recipient, message);

		SimpleCard card = new SimpleCard();
		card.setTitle("Message to " + recipient);
		card.setContent(message);

		final String response = "Message sent";
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(response);

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse handlePartialIntent(final Contact recipient) {

		return null;
	}

	private SpeechletResponse handlePartialIntent() {

		return null;
	}
}
