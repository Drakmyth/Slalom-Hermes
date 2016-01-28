package com.hokee.lambdas.users;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.hermes.services.DbService;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetUserForPinRequest;
import com.hokee.shared.results.GetUserResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetUserForPin {

	private static final Logger logger = LoggerFactory.getLogger(GetUserForPin.class);

	public static GetUserResult handleRequest(final GetUserForPinRequest request, final Context context) {

		final String user_pin = request.getPin();
		final User retVal;

		try {
			final String sql = String.format("SELECT * FROM \"Users\" WHERE pin='%s'", user_pin);
			final ResultSet results = DbService.executeQuery(sql);
			results.next();
			retVal = new User(results.getString("user_guid"), user_pin);
		} catch (final SQLException e) {
			final String errorMessage = "Failed accessing non-existent column.";

			logger.error(errorMessage, e);
			return GetUserResult.Failure(errorMessage);
		}

		return GetUserResult.Success(retVal);
	}
}
