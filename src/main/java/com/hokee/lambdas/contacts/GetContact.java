package com.hokee.lambdas.contacts;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.hermes.services.DbService;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetContactRequest;
import com.hokee.shared.results.GetContactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GetContact {

	private static final Logger logger = LoggerFactory.getLogger(GetContact.class);

	public static GetContactResult handleRequest(final GetContactRequest request, final Context context) {

		final String user_guid = request.getUserId();
		final String name = request.getContactName();

		final Contact retVal;

		try {
			// Lookup Contact
			String sql = String.format("SELECT * FROM \"Contacts\" WHERE user_guid='%s' AND name='%s'", user_guid, name);
			ResultSet results = DbService.executeQuery(sql);
			results.next();
			final String contact_guid = results.getString("contact_guid");

			// Build my User model
			sql = String.format("SELECT * FROM \"Users\" WHERE user_guid='%s'", user_guid);
			results = DbService.executeQuery(sql);
			results.next();
			final User user = new User(results.getString("user_guid"), results.getString("pin"));

			// Build Contact's user model
			sql = String.format("SELECT * FROM \"Users\" WHERE user_guid='%s'", contact_guid);
			results = DbService.executeQuery(sql);
			results.next();
			final User contactUser = new User(results.getString("user_guid"), results.getString("pin"));

			// Return
			retVal = new Contact(user, contactUser, name);
		} catch (final SQLException e) {
			final String errorMessage = "Failed accessing non-existent column.";

			logger.error(errorMessage, e);
			return GetContactResult.Failure(errorMessage);
		}

		return GetContactResult.Success(retVal);
	}
}
