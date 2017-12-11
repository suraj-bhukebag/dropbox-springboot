package com.cmpe273.dropbox.mappers;

import java.util.List;

import com.cmpe273.dropbox.model.Files;

public class UserActivityResponse extends GenericResponse {

	private List<Files> activity;

	public List<Files> getActivity() {
		return activity;
	}

	public void setActivity(List<Files> activity) {
		this.activity = activity;
	}
	
	
}
