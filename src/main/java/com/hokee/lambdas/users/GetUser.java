package com.hokee.lambdas.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetUserRequest;
import com.hokee.shared.results.GetUserResult;

public class GetUser {

	private static final Logger logger = LoggerFactory.getLogger(GetUser.class);

	public static GetUserResult handleRequest(final GetUserRequest request, final Context context) {

		return GetUserResult.Success(new User("id", "name"));
	}
}
