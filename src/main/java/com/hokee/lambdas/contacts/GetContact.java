package com.hokee.lambdas.contacts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.models.Contact;
import com.hokee.shared.requests.GetContactRequest;
import com.hokee.shared.models.User;

public class GetContact {
	private static final Logger logger = LoggerFactory.getLogger(GetContact.class);

	public static Contact handleRequest(final GetContactRequest contact, final Context context) {
		return new Contact(new User(), new User(), "contactName");
	}
}
