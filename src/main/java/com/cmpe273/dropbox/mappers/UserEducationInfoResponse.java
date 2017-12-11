package com.cmpe273.dropbox.mappers;

public class UserEducationInfoResponse extends GenericResponse {
	
	UserEducationInfoRequest eduinfo;

	public UserEducationInfoRequest getUserEducationInfoRequest() {
		return eduinfo;
	}

	public void setUserEducationInfoRequest(
			UserEducationInfoRequest userEducationInfoRequest) {
		this.eduinfo = userEducationInfoRequest;
	}
	
	

}
