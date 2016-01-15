package com.hokee.hermes.contexts.CheckMessage;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.shared.Message;

// TODO change to a wrapper that the session processor returns
public class CheckMessageContextProcessor {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageContext.class);

	private final CheckMessageContext _context;

	public CheckMessageContextProcessor(final CheckMessageContext context) {
		_context = context;
	}

	public void addMessages(final List<Message> messages) {
		log.info("adding {} messages to message list", messages.size());
		_context.setMessages(messages);
	}

	public Message getNextMessage() {
		log.info("getting message from list of size {}", _context.getMessages().size());
		return _context.remove(0);
	}

	public int messageCount() {
		return _context.getMessages().size();
	}

	public Message getPreviousMessage() {
		log.info("getting previous message {}", _context.getPreviousMessage());
		return _context.getPreviousMessage();
	}

	public void setPreviousMessage(final Message lastMessage) {
		_context.setPreviousMessage(lastMessage);
	}

	public CheckMessageContextStage getStage() {
		return _context.getStage();
	}

	public void setStage(CheckMessageContextStage stage) {
		_context.setStage(stage);
	}

	public CheckMessageContext getContext() {
		return _context;
	}
}
