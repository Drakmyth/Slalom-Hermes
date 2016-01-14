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
import com.hokee.hermes.contexts.CheckMessageContextStage;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.Message;

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

		// check to see if the context is wrong
		if (!(_sessionService.currentContext() == null)) {
			final CheckMessageContext context = new CheckMessageContext();
			context.setStage(CheckMessageContextStage.GET_MESSAGES);
			_sessionService.setContext(context);
		}

		final CheckMessageContext context = _sessionService.getContext();

		switch (context.getStage()) {
			case GET_MESSAGES:

				// get the message
				List<Message> messages = new ArrayList<>(_messageService.getMessages(_userService.getUser()));

				if (messages.size() == 0) {
					final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
					speech.setText("You have zero messages");

					return SpeechletResponse.newTellResponse(speech);
				}

				// set up the context
				context.addMessages(messages);
				context.setStage(CheckMessageContextStage.READ_NEXT);

				final Message message = context.getNextMessage();
				context.setPreviousMessage(message);
				_sessionService.setContext(context);

				return newCheckMessageAskResponse(
						"You have " + messages.size() + " messages. First message ",
						message.getMessage());

			case READ_NEXT:

				Slot actionSlot = intent.getSlot(ACTION);

				if (actionSlot == null || actionSlot.getValue() == null) {
					throw new SpeechletException("Action missing");
				}

				switch (CheckMessageContextAction.valueOf(actionSlot.getValue())) {
					case REPEAT:
						return newCheckMessageAskResponse("", context.getPreviousMessage().getMessage());
					case DELETE:
						_messageService.deleteMessage(context.getPreviousMessage().getId());
					case SAVE:
					default:
						if (context.messageCount() == 0) {
							final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
							speech.setText("You have no more messages");
							return SpeechletResponse.newTellResponse(speech);
						}

						final Message message1 = context.getNextMessage();
						context.setPreviousMessage(message1);
						_sessionService.setContext(context);

						return newCheckMessageAskResponse("Next message", message1.getMessage());
				}
		}
		// TODO clean up, remove exception and respond telling the user there was a problem
		throw new SpeechletException("Invalid Intent");
	}

	private SpeechletResponse newCheckMessageAskResponse(final String response, final String message) {
		final String prompt = "What would you like to do with this message? You can say delete, save, or repeat.";
		final String responseText = response + " beep " + message + " beep " + prompt;

		return newAskResponse(responseText, prompt);
	}
}
