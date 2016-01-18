package com.hokee.shared;

public class Contact {

	private User user;
	private User contact;
	private String name;

	public Contact(final User user, final User contact, final String name) {
		this.user = user;
		this.contact = contact;
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(final User contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
