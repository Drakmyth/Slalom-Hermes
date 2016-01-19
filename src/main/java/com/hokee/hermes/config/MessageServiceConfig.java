package com.hokee.hermes.config;

public class MessageServiceConfig {

	private String sendMessageAPIEndpoint = "https://6ztwwsksbj.execute-api.us-east-1.amazonaws.com/dev/message";
	private String getMessagesAPIEndpoint = "";
	private String deleteMessageAPIEndpoint = "";

	public String getSendMessageAPIEndpoint() {
		return sendMessageAPIEndpoint;
	}

	public String getGetMessagesAPIEndpoint() {
		return getMessagesAPIEndpoint;
	}

	public String getDeleteMessageAPIEndpoint() {
		return deleteMessageAPIEndpoint;
	}
}
