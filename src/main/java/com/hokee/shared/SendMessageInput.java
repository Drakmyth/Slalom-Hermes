package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageInput {

	@JsonProperty("sender")
	public String sender;

	@JsonProperty("recipient")
	public String recipient;

	@JsonProperty("message")
	public String message;
}
