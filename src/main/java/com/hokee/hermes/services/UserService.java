package com.hokee.hermes.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hokee.hermes.interfaces.IUserService;
import com.hokee.shared.User;

public class UserService implements IUserService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Override
	public User getUser() {
		log.info("getUser called");

		final User user = new User();
		user.setId("0000");

		return user;
	}
}
