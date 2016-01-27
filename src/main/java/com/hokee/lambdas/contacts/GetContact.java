package com.hokee.lambdas.contacts;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.models.Contact;
import com.hokee.shared.models.User;
import com.hokee.shared.requests.GetContactRequest;
import com.hokee.shared.results.GetContactResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetContact {

	private static final Logger logger = LoggerFactory.getLogger(GetContact.class);

	public static GetContactResult handleRequest(final GetContactRequest contact, final Context context) {

		return GetContactResult.Success(new Contact(new User(), new User(), "contactName"));
	}
}
