package com.hokee.lambdas.contacts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.requests.AddContactRequest;
import com.hokee.shared.results.AddContactResult;

public class AddContact {

	private static final Logger logger = LoggerFactory.getLogger(AddContact.class);

	public static AddContactResult handleRequest(final AddContactRequest request, final Context context) {

		return AddContactResult.Success();
	}
}
