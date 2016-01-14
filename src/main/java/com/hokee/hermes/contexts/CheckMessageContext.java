package com.hokee.hermes.contexts;

import java.util.ArrayList;
import java.util.List;
import com.hokee.shared.Message;

public class CheckMessageContext extends AbstractContext{

	private static final String context = "CheckMessage";

	private final List<Message> _messages;
	private CheckMessageContextStage _stage;
	private Message _previousMessage;
	private CheckMessageContextAction _currentAction;

	public CheckMessageContext() {
		_messages = new ArrayList<>();
		_previousMessage = null;
		_currentAction = null;
		_stage = CheckMessageContextStage.GET_MESSAGES;
	}

	public void addMessages(final List<Message> messages) {
		_messages.addAll(messages);
	}

	public Message getNextMessage() {
		return _messages.remove(0);
	}

	public int messageCount() {
		return _messages.size();
	}

	public Message getPreviousMessage() {
		return _previousMessage;
	}

	public void setPreviousMessage(final Message lastMessage) {
		_previousMessage = lastMessage;
	}

	public CheckMessageContextStage getStage() {
		return _stage;
	}

	public void setStage(CheckMessageContextStage stage) {
		_stage = stage;
	}
}
