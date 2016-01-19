package com.hokee.shared.results;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hokee.shared.models.Contact;

public class GetContactResult {

	private boolean _success;
	private Contact _contact;
	private String _errorMessage;

	@Deprecated
	public GetContactResult() {

		_success = false;
		_contact = null;
		_errorMessage = null;
	}

	@JsonCreator
	private GetContactResult(@JsonProperty("success") final boolean success,
	                         @JsonProperty("contact") final Contact contact,
	                         @JsonProperty("errorMessage") final String errorMessage) {

		_success = success;
		_contact = contact;
		_errorMessage = errorMessage;
	}

	public static GetContactResult Success(final Contact contact) {

		return new GetContactResult(true, contact, "Success");
	}

	public static GetContactResult Failure(final String errorMessage) {

		return new GetContactResult(false, null, errorMessage);
	}

	@JsonProperty("success")
	public boolean isSuccess() {

		return _success;
	}

	@Deprecated
	public void setSuccess(final boolean success) {

		_success = success;
	}

	@JsonProperty("contact")
	public Contact getContact() {

		return _contact;
	}

	@Deprecated
	public void setContact(final Contact contact) {

		_contact = contact;
	}

	@JsonProperty("errorMessage")
	public String getErrorMessage() {

		return _errorMessage;
	}

	@Deprecated
	public void setErrorMessage(final String errorMessage) {

		_errorMessage = errorMessage;
	}
}
