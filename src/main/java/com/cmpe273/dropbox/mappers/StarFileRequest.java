package com.cmpe273.dropbox.mappers;

public class StarFileRequest {

	private long id;
	
	private String path;
	
	private String star;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	
	
}
