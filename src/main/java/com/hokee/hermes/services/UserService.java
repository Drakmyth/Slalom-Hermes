package com.hokee.hermes.services;

import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService implements IUserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public User getUser() {

		log.info("getUser called");

		final User user = new User("0000", "Name");
		return user;
	}
}
