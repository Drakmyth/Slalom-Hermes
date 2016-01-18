package com.hokee.hermes.contexts.checkMessage;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.slu.Slot;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.hokee.hermes.HermesIntents;
import com.hokee.hermes.contexts.AbstractContextProcessor;
import com.hokee.hermes.interfaces.IContactService;
import com.hokee.hermes.interfaces.IMessageService;
import com.hokee.hermes.interfaces.ISessionService;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.Message;
import com.hokee.shared.User;

public class CheckMessageProcessor extends AbstractContextProcessor {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageProcessor.class);

	private final IMessageService _messageService;
	private final IContactService _contactService;
	private final IUserService _userService;
	private final ISessionService _sessionService;
	private final User _user;
	private final CheckMessageContextWrapper _context;

	private static final String ACTION = "action";

	public CheckMessageProcessor(final IMessageService messageService, final IContactService contactService, final IUserService userService, final ISessionService sessionService) {
		_messageService = messageService;
		_contactService = contactService;
		_userService = userService;
		_sessionService = sessionService;
		_user = _userService.getUser(_sessionService.getSession().getUser().getUserId());
		_context = new CheckMessageContextWrapper((CheckMessageContext)_sessionService.getContext());
	}

	@Override
	public SpeechletResponse handleRequest(final Intent intent) throws SpeechletException {

		log.info("context at stage={}", _context.getStage());

		switch (_context.getStage()) {
			case GET_MESSAGES:
				return getGetMessagesResponse(intent);
			case READ_NEXT_MESSAGE:
				return getReadNextResponse(intent);
		}

		throw new SpeechletException("Problem processing check message request");
	}

	private SpeechletResponse getGetMessagesResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in getMessages stage intent={}", intentName);

		if (HermesIntents.CheckMessage.name().equals(intentName)) {

			final List<Message> messages = new ArrayList<>(_messageService.getMessages(_user));

			log.info("found {} messages for user {}", messages.size(), _user.getName());
			if (messages.size() == 0) {
				final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
				speech.setText("You have zero messages");

				return SpeechletResponse.newTellResponse(speech);
			}

			log.info("adding messages to context");
			_context.addMessages(messages);

			log.info("setting stage to {}", CheckMessageContextStage.READ_NEXT_MESSAGE.name());
			_context.setStage(CheckMessageContextStage.READ_NEXT_MESSAGE);

			log.info("getting message to read");
			final Message message = _context.getNextMessage();

			log.info("setting previous message {}", message);
			_context.setPreviousMessage(message);

			log.info("saving context {}", _context);
			_sessionService.setContext(_context.getContext());

			log.info("return newAskResponse");
			return newCheckMessageAskResponse(
					"You have " + messages.size() + " messages. First message ",
					message.getMessage());
		}

		throw new SpeechletException("Issue in the get messages stage");
	}

	private SpeechletResponse newCheckMessageAskResponse(final String response, final String message) {
		final String prompt = "What would you like to do with this message? You can say delete, save, or repeat.";
		final String responseText = response + " beep " + message + " beep " + prompt;

		log.info("return newAskResponse with response text {}", responseText);
		return newAskResponse(responseText, prompt);
	}

	private SpeechletResponse getReadNextResponse(final Intent intent) throws SpeechletException {

		final String intentName = (intent != null) ? intent.getName() : null;
		log.info("in readNextMessage stage intent={}", intentName);

		if (HermesIntents.CheckMessage.name().equals(intentName)) {

			final Slot actionSlot = intent.getSlot(ACTION);

			if (actionSlot == null || actionSlot.getValue() == null) {
				throw new SpeechletException("Action missing");
			}

			log.info("action found {}", actionSlot.getValue());

			switch (CheckMessageContextAction.valueOf(actionSlot.getValue().toUpperCase())) {
				case REPEAT:
					return newCheckMessageAskResponse("", _context.getPreviousMessage().getMessage());
				case DELETE:
					_messageService.deleteMessage(_user, _context.getPreviousMessage().getId());
				case SAVE:
				default:
					if (_context.messageCount() == 0) {
						log.info("no new message - end session");
						final PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
						speech.setText("You have no more messages");
						return SpeechletResponse.newTellResponse(speech);
					}

					log.info("getting message to read");
					final Message message1 = _context.getNextMessage();
					_context.setPreviousMessage(message1);
					_sessionService.setContext(_context.getContext());

					return newCheckMessageAskResponse("Next message", message1.getMessage());
			}
		}

		throw new SpeechletException("Issue in the read next message stage");
	}
}
