package com.hokee.lambdas.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.DeleteMessageResult;

public class DeleteMessage {

	private static final Logger logger = LoggerFactory.getLogger(DeleteMessage.class);

	public static DeleteMessageResult handleRequest(final String messageId, final Context context) {
		final DeleteMessageResult result = new DeleteMessageResult();
		result.setMessage("");
		result.setSuccess(true);
		return result;
	}
}
