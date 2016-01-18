package com.hokee.lambdas.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.User;

public class GetUser {

	private static final Logger logger = LoggerFactory.getLogger(GetUser.class);

	public static User handleRequest(final String userId, final Context context) {
		return null;
	}
}