package com.cmpe273.dropbox.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cmpe273.dropbox.dao.PersonalInfoDao;
import com.cmpe273.dropbox.dao.UserDao;
import com.cmpe273.dropbox.mappers.SignUpRequest;
import com.cmpe273.dropbox.mappers.SignUpResponse;
import com.cmpe273.dropbox.mappers.SigninResponse;
import com.cmpe273.dropbox.mappers.UserPersonalInfoRequest;
import com.cmpe273.dropbox.mappers.UserPersonalInfoResponse;
import com.cmpe273.dropbox.mappers.UserResponse;
import com.cmpe273.dropbox.model.PersonalInfo;
import com.cmpe273.dropbox.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PersonalInfoDao personalInfoDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public SignUpResponse userSignup(SignUpRequest signupRequest) {

		SignUpResponse signUpResponse = new SignUpResponse();

		User user = userDao.findUserByEmail(signupRequest.getEmail());
		if (user != null) {
			signUpResponse.setCode("500");
			signUpResponse
					.setMsg("User with same email already exist. Please use different email.");
		} else {
			user = new User();
			user.setEmail(signupRequest.getEmail());
			user.setFirstname(signupRequest.getFirstname());
			user.setLastname(signupRequest.getLastname());
			user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			userDao.save(user);
			signUpResponse.setCode("200");
			signUpResponse.setMsg("User Signup Successfull.");

		}
		return signUpResponse;
	}

	public SigninResponse userSignin(SignUpRequest signupRequest) {

		SigninResponse signinResponse = new SigninResponse();

		User user = userDao.findUserByEmail(signupRequest.getEmail());
		if (user == null) {
			signinResponse.setCode("500");
			signinResponse.setMsg("Invalid Username.");
		} else {
			if (passwordEncoder.matches(signupRequest.getPassword(),
					user.getPassword())) {
				signinResponse.setLoggedIn(true);
				UserResponse userResponse = new UserResponse();
				userResponse.setFirstname(user.getFirstname());
				userResponse.setLastname(user.getLastname());
				userResponse.setEmail(user.getEmail());
				signinResponse.setUser(userResponse);
				signinResponse.setEduinfo(null);
				signinResponse.setPinfo(user.getPersonalInfo());
				signinResponse.setInterests(null);
				signinResponse.setCode("200");
				signinResponse.setMsg("Login Successful.");
			} else {
				signinResponse.setCode("500");
				signinResponse.setMsg("Invalid Password.");
			}
		}
		return signinResponse;
	}

	public UserPersonalInfoResponse userPersonalInfo(
			UserPersonalInfoRequest pinfoRequest) {

		UserPersonalInfoResponse userPersonalInfoResponse = new UserPersonalInfoResponse();
		PersonalInfo personalInfo = new PersonalInfo();
		personalInfo.setContactNumber(pinfoRequest.getContact());
		personalInfo.setDob(pinfoRequest.getDob());

		User user = userDao.findUserByEmail(pinfoRequest.getEmail());

		if (user.getPersonalInfo() != null) {

			PersonalInfo existingInfo = personalInfoDao.findOne(user
					.getPersonalInfo().getId());
			existingInfo.setContactNumber(pinfoRequest.getContact());
			existingInfo.setDob(pinfoRequest.getDob());
			personalInfoDao.save(existingInfo);

		} else {

			PersonalInfo savedPinfo = personalInfoDao.save(personalInfo);
			user.setPersonalInfo(savedPinfo);
			userDao.save(user);

		}

		userPersonalInfoResponse.setCode("200");
		userPersonalInfoResponse.setMsg("User personal info updated.");
		userPersonalInfoResponse.setPinfo(pinfoRequest);
		return userPersonalInfoResponse;
	}

}
