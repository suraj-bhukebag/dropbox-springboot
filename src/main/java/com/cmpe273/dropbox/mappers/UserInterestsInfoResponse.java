package com.cmpe273.dropbox.mappers;

import java.util.List;

import com.cmpe273.dropbox.model.Interests;

public class UserInterestsInfoResponse extends GenericResponse {

	private List<Interests> interests;

	public List<Interests> getInterests() {
		return interests;
	}

	public void setInterests(List<Interests> interests) {
		this.interests = interests;
	}
	
}
