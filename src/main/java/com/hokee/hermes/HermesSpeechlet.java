package com.hokee.hermes;

import static com.hokee.hermes.contexts.Context.AddContact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.hokee.hermes.contexts.Context;
import com.hokee.hermes.contexts.xaddContact.AddContactContext;
import com.hokee.hermes.contexts.xaddContact.AddContactProcessor;
import com.hokee.hermes.contexts.xheckMessage.CheckMessageContext;
import com.hokee.hermes.contexts.xheckMessage.CheckMessageProcessor;
import com.hokee.hermes.contexts.xsendMessage.SendMessageProcessor;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.hermes.services.SessionService;

public class HermesSpeechlet implements Speechlet {
	private static final Logger log = LoggerFactory.getLogger(HermesSpeechlet.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private ISessionService _sessionService;

	public HermesSpeechlet(final IMessageService messageService, final IContactService contactService, final IUserService userService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
	}

	@Override
	public void onSessionStarted(final SessionStartedRequest request, final Session session)
			throws SpeechletException {
		log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
				  session.getSessionId());

		_sessionService = new SessionService(session);
	}

	@Override
	public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
			throws SpeechletException {
		log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
				  session.getSessionId());
		return getWelcomeResponse();
	}

	private boolean setUpContext(final Intent intent) {

		final String intentName = (intent != null) ? intent.getName() : null;

		if (HermesIntents.SendMessage.name().equals(intentName)) {
			log.info("setting new context for {}", intentName);

			_sessionService.setCurrentContext(Context.SendMessage);
			return true;

		} else if (HermesIntents.CheckMessage.name().equals(intentName)) {
			log.info("setting new context for {}", intentName);

			_sessionService.setCurrentContext(Context.CheckMessages);
			final CheckMessageContext context = new CheckMessageContext();
			_sessionService.setContext(context);
			return true;

		} else if (HermesIntents.AddContact.name().equals(intentName)) {
			log.info("setting new context for {}", intentName);

			_sessionService.setCurrentContext(AddContact);
			final AddContactContext context = new AddContactContext();
			_sessionService.setContext(context);
			return true;

		}

		return false;
	}

	@Override
	public SpeechletResponse onIntent(final IntentRequest request, final Session session)
			throws SpeechletException {
		log.info("onIntent requestId={}, sessionId={}", request.getRequestId(),
				  session.getSessionId());

		// get context
		final Context context = _sessionService.currentContext();
		final Intent intent = request.getIntent();
		final String intentName = (intent != null) ? intent.getName() : null;

		log.info("onIntent context={}, intent={}", context, intentName);

		// if no context try and start a new one
		if (context == null) {
			// if no context was setup then handle one shot intent (intents that don't need contexts)
			if (!setUpContext(intent)) {
				if ("AMAZON.Help".equals(intentName)) {
					return getHelpResponse();
				} else {
					return getErrorResponse();
				}
			}
		}

		try {
			switch (_sessionService.currentContext()) {
				case AddContact:
					log.info("in xaddContact context");
					return new AddContactProcessor(_messageService, _contactService, _userService, _sessionService).handleRequest(intent);
				case CheckMessages:
					log.info("in xheckMessage context");
					return new CheckMessageProcessor(_messageService, _contactService, _userService, _sessionService).handleRequest(intent);
				case SendMessage:
					log.info("in SendMessage context");
					return new SendMessageProcessor(_messageService, _contactService, _userService, _sessionService).handleRequest(intent);
			}
		} catch (final SpeechletException ex) {
			final PlainTextOutputSpeech output = new PlainTextOutputSpeech();
			output.setText(ex.getMessage());

			return SpeechletResponse.newTellResponse(output);
		}

		throw new SpeechletException("There was a problem determining the context and or the intent");
	}

	@Override
	public void onSessionEnded(final SessionEndedRequest request, final Session session)
			throws SpeechletException {
		log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
				  session.getSessionId());
		// any cleanup logic goes here
	}

	/**
	 * Creates and returns a {@code SpeechletResponse} with a welcome message.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getWelcomeResponse() {
		String speechText = "Welcome to the Alexa Skills Kit, you can say hello";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	/**
	 * Creates a {@code SpeechletResponse} for the help intent.
	 *
	 * @return SpeechletResponse spoken and visual response for the given intent
	 */
	private SpeechletResponse getHelpResponse() {
		String speechText = "You can say hello to me!";

		// Create the Simple card content.
		SimpleCard card = new SimpleCard();
		card.setTitle("HelloWorld");
		card.setContent(speechText);

		// Create the plain text output.
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText(speechText);

		// Create reprompt
		Reprompt reprompt = new Reprompt();
		reprompt.setOutputSpeech(speech);

		return SpeechletResponse.newAskResponse(speech, reprompt, card);
	}

	private SpeechletResponse getErrorResponse() {
		return null;
	}
}