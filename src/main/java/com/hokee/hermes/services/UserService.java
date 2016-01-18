package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.User;

public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public User getUser(final String id) {
		log.info("getUser called");

		final User user = new User("0000", "Name");
		return user;
	}

	@Override
	public User getUserForPin(final String pin) {
		return getUser("0000");
	}
}
