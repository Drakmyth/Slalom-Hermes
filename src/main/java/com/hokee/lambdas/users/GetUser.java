package com.hokee.lambdas.users;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.hermes.services.DbService;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetUserRequest;
import com.hokee.shared.results.GetUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUser {

	private static final Logger logger = LoggerFactory.getLogger(GetUser.class);

	public static GetUserResult handleRequest(final GetUserRequest request, final Context context) {

		final String user_guid = request.getUserId();
		final User retVal;

		try {
			String sql = String.format("SELECT * FROM \"Users\" WHERE user_guid='%s'", user_guid);
			ResultSet results = DbService.executeQuery(sql);
			results.next();
			retVal = new User(user_guid, results.getString("pin"));
		} catch (final SQLException e) {
			final String errorMessage = "Failed accessing non-existent column.";

			logger.error(errorMessage, e);
			return GetUserResult.Failure(errorMessage);
		}

		return GetUserResult.Success(retVal);
	}
}
