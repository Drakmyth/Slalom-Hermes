package com.hokee.hermes.interfaces;

import com.hokee.shared.models.User;

public interface IUserService {
	User getUser(final String id);
	User getUserForPin(final String pin);
}
