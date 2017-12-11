package com.cmpe273.dropbox.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe273.dropbox.dao.EducationInfoDao;
import com.cmpe273.dropbox.dao.InterestsDao;
import com.cmpe273.dropbox.dao.PersonalInfoDao;
import com.cmpe273.dropbox.dao.UserDao;
import com.cmpe273.dropbox.mappers.SignUpRequest;
import com.cmpe273.dropbox.mappers.SignUpResponse;
import com.cmpe273.dropbox.mappers.SigninResponse;
import com.cmpe273.dropbox.mappers.UserEducationInfoRequest;
import com.cmpe273.dropbox.mappers.UserEducationInfoResponse;
import com.cmpe273.dropbox.mappers.UserInterestInfoRequest;
import com.cmpe273.dropbox.mappers.UserInterestsInfoResponse;
import com.cmpe273.dropbox.mappers.UserPersonalInfoRequest;
import com.cmpe273.dropbox.mappers.UserPersonalInfoResponse;
import com.cmpe273.dropbox.mappers.UserResponse;
import com.cmpe273.dropbox.model.EducationInfo;
import com.cmpe273.dropbox.model.Interests;
import com.cmpe273.dropbox.model.PersonalInfo;
import com.cmpe273.dropbox.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PersonalInfoDao personalInfoDao;

	@Autowired
	private EducationInfoDao educationInfoDao;
	
	@Autowired
	private InterestsDao interestsDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public SignUpResponse userSignup(SignUpRequest signupRequest) {

		SignUpResponse signUpResponse = new SignUpResponse();

		User user = userDao.findUserByEmail(signupRequest.getEmail());
		if (user != null) {
			signUpResponse.setCode(500);
			signUpResponse
					.setMsg("User with same email already exist. Please use different email.");
		} else {
			user = new User();
			user.setEmail(signupRequest.getEmail());
			user.setFirstname(signupRequest.getFirstname());
			user.setLastname(signupRequest.getLastname());
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			userDao.save(user);
			signUpResponse.setCode(200);
			signUpResponse.setMsg("User Signup Successfull.");

		}
		return signUpResponse;
	}

	public SigninResponse userSignin(SignUpRequest signupRequest) {

		SigninResponse signinResponse = new SigninResponse();

		User user = userDao.findUserByEmail(signupRequest.getEmail());
		if (user == null) {
			signinResponse.setCode(500);
			signinResponse.setMsg("Invalid Username.");
		} else {
			if (passwordEncoder.matches(signupRequest.getPassword(),
					user.getPassword())) {
				signinResponse.setLoggedIn(true);
				UserResponse userResponse = new UserResponse();
				userResponse.setId(user.getId());
				userResponse.setFname(user.getFirstname());
				userResponse.setLname(user.getLastname());
				userResponse.setEmail(user.getEmail());
				signinResponse.setUser(userResponse);
				signinResponse.setEduinfo(user.getEducationInfo());
				signinResponse.setPinfo(user.getPersonalInfo());
				signinResponse.setInterests(user.getInterests());
				signinResponse.setCode(200);
				signinResponse.setMsg("Login Successful.");
			} else {
				signinResponse.setCode(500);
				signinResponse.setMsg("Invalid Password.");
			}
		}
		return signinResponse;
	}

	public UserPersonalInfoResponse userPersonalInfo(
			UserPersonalInfoRequest pinfoRequest) {

		User user = userDao.findUserByEmail(pinfoRequest.getEmail());
		PersonalInfo personalInfo = null;
		boolean isExist = false;

		if (user.getPersonalInfo() != null) {

			personalInfo = personalInfoDao.findOne(user
					.getPersonalInfo().getId());
			personalInfo.setContactNumber(pinfoRequest.getContact());
			personalInfo.setDob(pinfoRequest.getDob());
			isExist = true;
			

		} else {
			personalInfo = new PersonalInfo();
			personalInfo.setContactNumber(pinfoRequest.getContact());
			personalInfo.setDob(pinfoRequest.getDob());		

		}
		
		PersonalInfo savedPinfo = personalInfoDao.save(personalInfo);
		if(!isExist) {
			user.setPersonalInfo(savedPinfo);
			userDao.save(user);
		}		
	
		UserPersonalInfoResponse userPersonalInfoResponse = new UserPersonalInfoResponse();
		userPersonalInfoResponse.setCode(200);
		userPersonalInfoResponse.setMsg("User personal info updated.");
		userPersonalInfoResponse.setPinfo(pinfoRequest);
		return userPersonalInfoResponse;
	}

	public UserEducationInfoResponse userEducationInfo(
			UserEducationInfoRequest eduinfoRequest) {

		boolean isExist = false;
		User user = userDao.findUserByEmail(eduinfoRequest.getEmail());
		EducationInfo educationInfo = null;
		if (user.getEducationInfo() != null) {
			educationInfo = educationInfoDao.findOne(user.getEducationInfo()
					.getId());
			isExist = true;

		} else {
			educationInfo = new EducationInfo();
		}

		educationInfo.setCollegeName(eduinfoRequest.getCollegeName());
		educationInfo.setEndDate(eduinfoRequest.getEndDate());
		educationInfo.setStartDate(eduinfoRequest.getStartDate());
		educationInfo.setGpa(eduinfoRequest.getGpa());
		educationInfo.setMajor(eduinfoRequest.getMajor());
		EducationInfo savedEduInfo = educationInfoDao.save(educationInfo);
		
		if(!isExist) {
			user.setEducationInfo(savedEduInfo);
			userDao.save(user);
		}
		
		UserEducationInfoResponse userEducationInfoResponse = new UserEducationInfoResponse();
		userEducationInfoResponse.setCode(200);
		userEducationInfoResponse.setMsg("Education Info Updated.");
		userEducationInfoResponse.setUserEducationInfoRequest(eduinfoRequest);
		
		
		return userEducationInfoResponse;
	}

	public UserInterestsInfoResponse userInterestInfo(
			UserInterestInfoRequest intinfoRequest) {
		
		User user = userDao.findOne(intinfoRequest.getUserId());
		Interests interests = new Interests();
		interests.setComment(intinfoRequest.getComment());
		interests.setInterest(intinfoRequest.getInterest());
		interests.setUser(user);		
		interestsDao.save(interests);
		
		user.getInterests().add(interests);
		userDao.save(user);
		
		UserInterestsInfoResponse userInterestsInfoResponse = new UserInterestsInfoResponse();
		userInterestsInfoResponse.setCode(200);
		userInterestsInfoResponse.setMsg("User Interests");
		userInterestsInfoResponse.setInterests(interestsDao.findInterestsByUser(intinfoRequest.getUserId()));
		
		return userInterestsInfoResponse;
	}

}
