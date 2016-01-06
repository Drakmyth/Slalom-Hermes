package com.hokee.sendmessage;

import com.amazonaws.services.lambda.runtime.Context;
import com.hokee.shared.SendMessageInput;
import com.hokee.shared.SendMessageOutput;

public class App {

	public static SendMessageOutput handleRequest(final SendMessageInput input, final Context context) {

		SendMessageOutput output = new SendMessageOutput();
		output.message = "Test";
		output.success = true;

		return output;
	}
}
