package com.cmpe273.dropbox.mappers;

public class UserPersonalInfoResponse extends GenericResponse {

	private UserPersonalInfoRequest pinfo;

	public UserPersonalInfoRequest getPinfo() {
		return pinfo;
	}

	public void setPinfo(UserPersonalInfoRequest pinfo) {
		this.pinfo = pinfo;
	}
	
	
}
