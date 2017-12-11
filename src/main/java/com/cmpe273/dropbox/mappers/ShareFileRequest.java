package com.cmpe273.dropbox.mappers;

public class ShareFileRequest {
	
	private String path;
	
	private String email;
	
	private String sharedWith;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSharedWith() {
		return sharedWith;
	}

	public void setSharedWith(String sharedWith) {
		this.sharedWith = sharedWith;
	}
	

}
