package com.hokee.hermes;

import java.util.HashSet;
import java.util.Set;
import org.springframework.web.client.RestTemplate;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.hokee.hermes.config.MessageServiceConfig;
import com.hokee.hermes.services.ContactService;
import com.hokee.hermes.services.MessageService;
import com.hokee.hermes.services.UserService;

/**
 * Hello world!
 *
 */
public class Hermes extends SpeechletRequestStreamHandler {

	private static final Set<String> supportedApplicationIds = new HashSet<String>();
	private static MessageService messageService;
	private static ContactService contactService;
	private static UserService userService;

	static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
		supportedApplicationIds.add("amzn1.echo-sdk-ams.app.65301810-449f-447b-a5d7-58f5b97258a4");

		messageService = new MessageService(new MessageServiceConfig(), new RestTemplate());
		contactService = new ContactService();
		userService = new UserService();
	}

	public Hermes() {
		super(new HermesSpeechlet(messageService, contactService, userService), supportedApplicationIds);
	}
}
