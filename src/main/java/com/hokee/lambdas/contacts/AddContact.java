package com.hokee.lambdas.contacts;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.models.Contact;
import com.hokee.shared.results.AddMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddContact {

	private static final Logger logger = LoggerFactory.getLogger(AddContact.class);

	public static AddMessageResult handleRequest(final Contact contact, final Context context) {

		return AddMessageResult.Success();
	}
}
