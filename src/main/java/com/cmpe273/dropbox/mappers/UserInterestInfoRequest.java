package com.cmpe273.dropbox.mappers;

public class UserInterestInfoRequest {
	
	private long userId;
	
	private String comment;
	
	private String interest;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}
	
	
	
	
	
	

}
