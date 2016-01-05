package com.hokee.hermes;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.hokee.hermes.services.ContactService;
import com.hokee.hermes.services.MessageService;

/**
 * Hello world!
 *
 */
public class App extends SpeechletRequestStreamHandler {
	private static final Set<String> supportedApplicationIds = new HashSet<String>();
	static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
		supportedApplicationIds.add("amzn1.echo-sdk-ams.app.65301810-449f-447b-a5d7-58f5b97258a4");
	}

	public App() {
		super(new HermesSpeechlet(new MessageService(), new ContactService()), supportedApplicationIds);
	}
}
