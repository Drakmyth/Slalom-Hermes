package com.hokee.shared;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	private String _id;
	private String _name;

	@Deprecated
	public User() {

		_id = null;
		_name = null;
	}

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

	@Deprecated
	public void setId(final String id) {

		_id = id;
	}

	@JsonProperty("name")
	public String getName() {

		return _name;
	}

	public void setName(final String name) {

		_name = name;
	}
}
