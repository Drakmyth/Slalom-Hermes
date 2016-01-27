package com.hokee.hermes.contexts;

import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.hokee.hermes.interfaces.IContextProcessor;

public abstract class AbstractContextProcessor implements IContextProcessor {

	protected SpeechletResponse newAskResponse(final String responseText, final String repromptText) {

		final PlainTextOutputSpeech responseSpeech = new PlainTextOutputSpeech();
		responseSpeech.setText(responseText);

		final PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
		repromptSpeech.setText(repromptText);

		final Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(repromptSpeech);

		return SpeechletResponse.newAskResponse(responseSpeech, reprompt);
	}

	protected SpeechletResponse newTellResponse(final String responseText) {
		final PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
		outputSpeech.setText(responseText);

		return SpeechletResponse.newTellResponse(outputSpeech);
	}
}
