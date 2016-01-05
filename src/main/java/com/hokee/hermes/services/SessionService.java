package com.hokee.hermes.services;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.User;
import com.hokee.hermes.interfaces.ISessionService;

public class SessionService implements ISessionService {

	private final Session _session;

	public SessionService(final Session session) {
		_session = session;
	}

	@Override
	public User getUser() {
		return _session.getUser();
	}
}
