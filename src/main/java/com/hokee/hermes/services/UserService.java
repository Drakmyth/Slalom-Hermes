package com.hokee.hermes.services;

import com.hokee.hermes.interfaces.IUserService;
import com.hokee.hermes.models.User;

public class UserService implements IUserService {

	@Override
	public User getUser() {

		final User user = new User();
		user.setId("0000");

		return user;
	}
}
