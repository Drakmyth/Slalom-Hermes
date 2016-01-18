package com.hokee.shared;

public class GetContactRequest {

	private String _userId;
	private String _contactName;

	public GetContactRequest(final String userId, final String contactName) {
		_userId = userId;
		_contactName = contactName;
	}

	public String getUserId() {
		return _userId;
	}

	public void setUserId(final String userId) {
		_userId = userId;
	}

	public String getContactName() {
		return _contactName;
	}

	public void setContactName(final String contactName) {
		_contactName = contactName;
	}
}
