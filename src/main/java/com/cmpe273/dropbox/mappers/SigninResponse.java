package com.cmpe273.dropbox.mappers;

import java.util.List;

import com.cmpe273.dropbox.model.EducationInfo;
import com.cmpe273.dropbox.model.Interests;
import com.cmpe273.dropbox.model.PersonalInfo;
import com.cmpe273.dropbox.model.User;

public class SigninResponse extends GenericResponse {

	private boolean loggedIn;
	
	private UserResponse user;
	
	private PersonalInfo pinfo;
	
	private EducationInfo eduinfo;
	
	private List<Interests> interests;

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

	public List<Interests> getInterests() {
		return interests;
	}

	public void setInterests(List<Interests> interests) {
		this.interests = interests;
	}
	
	

}
