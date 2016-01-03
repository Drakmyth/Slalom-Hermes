package com.hokee.hermes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;

public class SendMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(SendMessageHandler.class);

	private static final String MESSAGE = "message";
	private static final String FRIEND = "friend";

	public SpeechletResponse getSendMessageResponse(final Intent intent) throws SpeechletException {

		final String friend = intent.getSlot(FRIEND).getValue();
		final String message = intent.getSlot(MESSAGE).getValue();

		if (friend != null && message != null) {
			return handleFullIntent(friend, message);
		} else if (friend == null && message == null) {
			return handlePartialResponse();
		} else if (friend != null && message == null) {
			return handlePartialResponse(friend);
		} else {
			throw new SpeechletException("Invalid Intent");
		}
	}

	private SpeechletResponse handleFullIntent(final String friend, final String message) {

		final String response = "You sent the following message to {}, {}";

		SimpleCard card = new SimpleCard();
		card.setTitle("Message To: " + friend);
		card.setContent(message);

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(String.format(response, friend, message));

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse handlePartialResponse(final String friend) {

		final String response = "You sent the following message to {}, {}";

		SimpleCard card = new SimpleCard();
		card.setTitle("Message To: " + friend);
		card.setContent("message");

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(String.format(response, friend, "message"));

		return SpeechletResponse.newTellResponse(speech, card);
	}

	private SpeechletResponse handlePartialResponse() {

		final String response = "You sent the following message to {}, {}";

		SimpleCard card = new SimpleCard();
		card.setTitle("Message To: " + "");
		card.setContent("message");

		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(String.format(response, "", "message"));

		return SpeechletResponse.newTellResponse(speech, card);
	}
}
