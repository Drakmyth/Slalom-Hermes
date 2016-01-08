package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.User;
import com.hokee.hermes.interfaces.ISessionService;

public class SessionService implements ISessionService {
	private static final Logger log = LoggerFactory.getLogger(SessionService.class);

	private final Session _session;

	public SessionService(final Session session) {
		_session = session;
	}

	@Override
	public User getUser() {
		log.info("getUser called");

		return _session.getUser();
	}
}
