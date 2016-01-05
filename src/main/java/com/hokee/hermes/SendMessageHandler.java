package com.hokee.hermes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.User;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.models.Contact;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class SendMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	private static final String MESSAGE = "message";
	private static final String RECIPIENT = "friend";

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final ISessionService _sessionService;

	public SendMessageHandler(final IMessageService messageService, final IContactService contactService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_sessionService = sessionService;
	}

	public SpeechletResponse getSendMessageResponse(final Intent intent) throws SpeechletException {

		final User user = _sessionService.getUser();
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

		throw new NotImplementedException();
	}

	private SpeechletResponse handlePartialIntent() {

		throw new NotImplementedException();
	}
}
