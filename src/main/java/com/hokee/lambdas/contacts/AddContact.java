package com.hokee.lambdas.contacts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.results.AddMessageResult;
import com.hokee.shared.models.Contact;

public class AddContact {
	private static final Logger logger = LoggerFactory.getLogger(AddContact.class);

	public static AddMessageResult handleRequest(final Contact contact, final Context context) {
		final AddMessageResult result = new AddMessageResult();
		result.setMessage("");
		result.setSuccess(true);
		return result;
	}
}
