package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Contact extends User {

	@Deprecated
	public Contact() {

		super();
	}

	@JsonCreator
	public Contact(@JsonProperty("id") final String id,
	               @JsonProperty("name") final String name) {

		super(id, name);
	}
}
