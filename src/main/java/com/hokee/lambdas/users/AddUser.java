package com.hokee.lambdas.users;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.hermes.services.DbService;
import com.hokee.shared.requests.AddUserRequest;
import com.hokee.shared.results.AddUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddUser {

	private static final Logger logger = LoggerFactory.getLogger(AddUser.class);

	public static AddUserResult handleRequest(final AddUserRequest request, final Context context) {

		final String user_guid = request.getUserId();
		final String user_pin = request.getPin();
		
		DbService.execute(String.format("INSERT INTO \"Users\" (user_guid, pin) VALUES ('%s', '%s')", user_guid, user_pin));

		return AddUserResult.Success();
	}
}
