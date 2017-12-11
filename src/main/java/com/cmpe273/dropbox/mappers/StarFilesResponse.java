package com.cmpe273.dropbox.mappers;

import java.util.List;

import com.cmpe273.dropbox.model.Files;

public class StarFilesResponse extends GenericResponse {

	private List<Files> starred;

	public List<Files> getStarred() {
		return starred;
	}

	public void setStarred(List<Files> starred) {
		this.starred = starred;
	}
	
	
}
