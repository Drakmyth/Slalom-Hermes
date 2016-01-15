package com.hokee.lambdas.getmessages;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.Message;
import com.hokee.shared.User;

public class GetMessages {

	private static final Logger logger = LoggerFactory.getLogger(GetMessages.class);

	public static List<Message> handleRequest(final User user, final Context context) {
		return null;
	}
}
