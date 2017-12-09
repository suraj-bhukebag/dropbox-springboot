package com.cmpe273.dropbox.mappers;

public class UserEducationInfoResponse extends GenericResponse {
	
	UserEducationInfoRequest userEducationInfoRequest;

	public UserEducationInfoRequest getUserEducationInfoRequest() {
		return userEducationInfoRequest;
	}

	public void setUserEducationInfoRequest(
			UserEducationInfoRequest userEducationInfoRequest) {
		this.userEducationInfoRequest = userEducationInfoRequest;
	}
	
	

}
