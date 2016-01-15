package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private final String _id;
	private final String _name;

	@JsonCreator
	public User(@JsonProperty("id") final String id,
	            @JsonProperty("name") final String name) {

		_id = id;
		_name = name;
	}

	@JsonProperty("id")
	public String getId() {

		return _id;
	}

	@JsonProperty("name")
	public String getName() {

		return _name;
	}
}
