package com.hokee.hermes.interfaces;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;

public interface IIntentHandler {
	SpeechletResponse handleIntent(final Intent intent) throws SpeechletException;
}
