package com.hokee.hermes.interfaces;

import com.amazon.speech.speechlet.Session;
import com.hokee.hermes.contexts.AbstractContext;
import com.hokee.hermes.contexts.Context;

public interface ISessionService {
	Session getSession();
	Context currentContext();
	void setCurrentContext(final Context context);
	<T> T getContext();
	void setContext(final AbstractContext context);
}
