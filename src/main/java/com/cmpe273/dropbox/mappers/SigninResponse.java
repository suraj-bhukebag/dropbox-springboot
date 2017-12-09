package com.cmpe273.dropbox.mappers;

import java.util.List;
import java.util.Set;

import com.cmpe273.dropbox.model.EducationInfo;
import com.cmpe273.dropbox.model.Interests;
import com.cmpe273.dropbox.model.PersonalInfo;

public class SigninResponse extends GenericResponse {

	private boolean loggedIn;
	
	private UserResponse user;
	
	private PersonalInfo pinfo;
	
	private EducationInfo eduinfo;
	
	private Set<Interests> interests;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public UserResponse getUser() {
		return user;
	}

	public void setUser(UserResponse user) {
		this.user = user;
	}

	public PersonalInfo getPinfo() {
		return pinfo;
	}

	public void setPinfo(PersonalInfo pinfo) {
		this.pinfo = pinfo;
	}

	public EducationInfo getEduinfo() {
		return eduinfo;
	}

	public void setEduinfo(EducationInfo eduinfo) {
		this.eduinfo = eduinfo;
	}

	public Set<Interests> getInterests() {
		return interests;
	}

	public void setInterests(Set<Interests> interests) {
		this.interests = interests;
	}
	
	

}
