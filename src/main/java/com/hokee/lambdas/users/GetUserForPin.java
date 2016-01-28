package com.hokee.lambdas.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetUserForPinRequest;
import com.hokee.shared.results.GetUserResult;

public class GetUserForPin {

	private static final Logger logger = LoggerFactory.getLogger(GetUserForPin.class);

	public static GetUserResult handleRequest(final GetUserForPinRequest request, final Context context) {

		return GetUserResult.Success(new User("id", "name"));
	}
}
