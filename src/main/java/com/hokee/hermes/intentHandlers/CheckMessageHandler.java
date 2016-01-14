package com.hokee.hermes.intentHandlers;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.hokee.hermes.contexts.CheckMessageContext;
import com.hokee.hermes.contexts.CheckMessageContextAction;
import com.hokee.hermes.contexts.CheckMessageContextProcessor;
import com.hokee.hermes.contexts.CheckMessageContextStage;
import com.hokee.hermes.contexts.Context;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.Message;
import com.hokee.shared.User;

public class CheckMessageHandler extends AbstractMessageHandler {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageHandler.class);

	public static final String ACTION = "action";

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;

	public CheckMessageHandler(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
	}

	@Override
	public SpeechletResponse handleIntent(final Intent intent) throws SpeechletException {

		log.info("handleIntent intent={}", intent);

		final Slot actionSlot = intent.getSlot(ACTION);
		if (_sessionService.currentContext() == null && actionSlot.getValue() != null) {
			throw new SpeechletException("Intent started with no context by action word");
		}

		// check to see if the context is missing
		if (_sessionService.currentContext() == null) {
			log.info("no context found, starting new context CheckMessageContext");
			final CheckMessageContext context = new CheckMessageContext();
			context.setStage(CheckMessageContextStage.GET_MESSAGES);
			_sessionService.setContext(context);
			_sessionService.setCurrentContext(Context.CheckMessages);
		}

		final CheckMessageContext context = _sessionService.getContext();
		final CheckMessageContextProcessor contextProcessor = new CheckMessageContextProcessor(context);
		final User user = _userService.getUser();

		log.info("CheckMessageContext at state={}", context.getStage());
		switch (context.getStage()) {
			case GET_MESSAGES:

				// get the message
				List<Message> messages = new ArrayList<>(_messageService.getMessages(user));

				log.info("found {} messages for user {}", messages.size(), user.getName());
				if (messages.size() == 0) {
					final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
					speech.setText("You have zero messages");

					return SpeechletResponse.newTellResponse(speech);
				}

				log.info("adding messages to context");
				contextProcessor.addMessages(messages);

				log.info("setting stage to {}", CheckMessageContextStage.READ_NEXT.name());
				contextProcessor.setStage(CheckMessageContextStage.READ_NEXT);

				log.info("getting message to read");
				final Message message = contextProcessor.getNextMessage();

				log.info("setting previous message {}", message);
				contextProcessor.setPreviousMessage(message);

				log.info("saving context {}", context);
				_sessionService.setContext(contextProcessor.getContext());

				log.info("return newCheckMessageAskResponse");
				return newCheckMessageAskResponse(
						"You have " + messages.size() + " messages. First message ",
						message.getMessage());

			case READ_NEXT:

				if (actionSlot == null || actionSlot.getValue() == null) {
					throw new SpeechletException("Action missing");
				}

				log.info("action found {}", actionSlot.getValue());

				switch (CheckMessageContextAction.valueOf(actionSlot.getValue().toUpperCase())) {
					case REPEAT:
						return newCheckMessageAskResponse("", contextProcessor.getPreviousMessage().getMessage());
					case DELETE:
						_messageService.deleteMessage(contextProcessor.getPreviousMessage().getId());
					case SAVE:
					default:
						if (contextProcessor.messageCount() == 0) {
							log.info("no new message - end session");
							final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
							speech.setText("You have no more messages");
							return SpeechletResponse.newTellResponse(speech);
						}

						log.info("getting message to read");
						final Message message1 = contextProcessor.getNextMessage();
						contextProcessor.setPreviousMessage(message1);
						_sessionService.setContext(contextProcessor.getContext());

						return newCheckMessageAskResponse("Next message", message1.getMessage());
				}
		}
		// TODO clean up, remove exception and respond telling the user there was a problem
		throw new SpeechletException("Error determining Context and Intent");
	}

	private SpeechletResponse newCheckMessageAskResponse(final String response, final String message) {
		final String prompt = "What would you like to do with this message? You can say delete, save, or repeat.";
		final String responseText = response + " beep " + message + " beep " + prompt;

		log.info("return newAskResponse with response text {}", responseText);
		return newAskResponse(responseText, prompt);
	}
}
