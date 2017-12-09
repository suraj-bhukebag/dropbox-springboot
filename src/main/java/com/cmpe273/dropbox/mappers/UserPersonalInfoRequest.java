package com.cmpe273.dropbox.mappers;

public class UserPersonalInfoRequest {
	
	private String email;
	
	private String contact;
	
	private long dob;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public long getDob() {
		return dob;
	}

	public void setDob(long dob) {
		this.dob = dob;
	}
	
}
