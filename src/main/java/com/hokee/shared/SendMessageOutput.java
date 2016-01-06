package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendMessageOutput {

	@JsonProperty("success")
	public boolean success;

	@JsonProperty("message")
	public String message;
}
