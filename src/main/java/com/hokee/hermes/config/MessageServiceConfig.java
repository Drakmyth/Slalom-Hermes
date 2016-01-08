package com.hokee.hermes.config;

public class MessageServiceConfig {

	private String sendMessageAPIEndpoint = "https://6ztwwsksbj.execute-api.us-east-1.amazonaws.com/dev/message";

	public String getSendMessageAPIEndpoint() {
		return sendMessageAPIEndpoint;
	}
}
