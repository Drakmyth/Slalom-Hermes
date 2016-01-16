package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazon.speech.speechlet.Session;
import com.hokee.hermes.contexts.AbstractContext;
import com.hokee.hermes.contexts.Context;
import com.hokee.hermes.interfaces.ISessionService;

public class SessionService implements ISessionService {
	private static final Logger log = LoggerFactory.getLogger(SessionService.class);

	private final static String CONTEXT = "context";
	private final static String CURRENT_CONTEXT = "current_context";

	private final Session _session;

	public SessionService(final Session session) {
		_session = session;
	}

	@Override
	public Session getSession() {
		return _session;
	}

	@Override
	public Context currentContext() {
		return (Context) _session.getAttribute(CURRENT_CONTEXT);
	}

	@Override
	public void setCurrentContext(final Context context) {
		_session.setAttribute(CURRENT_CONTEXT, context);
	}

	@Override
	public <T> T getContext() {
		log.info("attributes {}", _session.getAttributes());
		return (T) _session.getAttribute(CONTEXT);
	}

	@Override
	public void setContext(final AbstractContext context) {
		_session.setAttribute(CONTEXT, context);
	}
}
