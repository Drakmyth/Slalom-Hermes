package com.hokee.lambdas.messages;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.requests.DeleteMessageRequest;
import com.hokee.shared.results.DeleteMessageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeleteMessage {

	private static final Logger logger = LoggerFactory.getLogger(DeleteMessage.class);

	public static DeleteMessageResult handleRequest(final DeleteMessageRequest request, final Context context) {

		return DeleteMessageResult.Success();
	}
}
