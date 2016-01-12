package com.hokee.hermes.contexts;

import java.util.ArrayList;
import java.util.List;
import com.hokee.shared.Message;

public class CheckMessageContext {

	private final List<Message> _messages;
	private Message _lastMessage;
	private CheckMessageContextAction _currentAction;

	public CheckMessageContext() {
		_messages = new ArrayList<>();
		_lastMessage = null;
		_currentAction = null;
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

	public Message getLastMessage() {
		return _lastMessage;
	}

	public void setLastMessage(final Message _lastMessage) {
		this._lastMessage = _lastMessage;
	}


}
