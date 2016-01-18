package com.hokee.hermes.contexts.checkMessage;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.contexts.AbstractContext;
import com.hokee.shared.Message;

public class CheckMessageContext extends AbstractContext {
	private static final Logger log = LoggerFactory.getLogger(CheckMessageContext.class);

	private List<Message> _messages;
	private CheckMessageContextStage _stage;
	private Message _previousMessage;

	public CheckMessageContext() {
		_messages = new ArrayList<>();
		_previousMessage = null;
		_stage = CheckMessageContextStage.GET_MESSAGES;
	}

	public List<Message> getMessages() {
		return _messages;
	}

	public void setMessages(final List<Message> _messages) {
		this._messages = _messages;
	}

	public CheckMessageContextStage getStage() {
		return _stage;
	}

	public void setStage(final CheckMessageContextStage _stage) {
		this._stage = _stage;
	}

	public Message getPreviousMessage() {
		return _previousMessage;
	}

	public void setPreviousMessage(final Message _previousMessage) {
		this._previousMessage = _previousMessage;
	}

	public Message remove(final int index) {
		return _messages.remove(index);
	}
}
