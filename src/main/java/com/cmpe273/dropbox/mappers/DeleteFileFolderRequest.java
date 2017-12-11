package com.cmpe273.dropbox.mappers;

public class DeleteFileFolderRequest {
	
	private String path;
	
	private String email;
	
	private boolean directory;	
	
	private long id;

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

	public boolean getDirectory() {
		return directory;
	}

	public void setDirectory(boolean isDirectory) {
		this.directory = isDirectory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	

}
