package com.hokee.lambdas.contacts;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.hermes.services.DbService;
import com.hokee.shared.requests.AddContactRequest;
import com.hokee.shared.results.AddContactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddContact {

	private static final Logger logger = LoggerFactory.getLogger(AddContact.class);

	public static AddContactResult handleRequest(final AddContactRequest request, final Context context) {

		final String user_guid = request.getUserId();
		final String contact_pin = request.getContactPin();
		final String name = request.getContactName();

		try {
			String sql = String.format("SELECT * FROM Users WHERE pin=='%s'", contact_pin);
			ResultSet results = DbService.executeQuery(sql);
			final String contact_guid = results.getString("user_guid");

			sql = String.format("INSERT INTO Contacts (user_guid, contact_guid, name) VALUES ('%s', '%s', '%s')", user_guid, contact_guid, name);
			DbService.execute(sql);
		} catch (final SQLException e) {
			final String errorMessage = "Failed accessing non-existent column.";

			logger.error(errorMessage, e);
			return AddContactResult.Failure(errorMessage);
		}

		return AddContactResult.Success();
	}
}
