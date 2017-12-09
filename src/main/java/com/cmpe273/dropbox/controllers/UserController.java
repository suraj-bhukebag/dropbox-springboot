package com.cmpe273.dropbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cmpe273.dropbox.mappers.SignUpRequest;
import com.cmpe273.dropbox.mappers.SignUpResponse;
import com.cmpe273.dropbox.mappers.SigninResponse;
import com.cmpe273.dropbox.mappers.UserPersonalInfoRequest;
import com.cmpe273.dropbox.mappers.UserPersonalInfoResponse;
import com.cmpe273.dropbox.services.UserService;

@Controller
// @CrossOrigin(origins = "http://localhost:3000")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(path = "signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userSignUp(@RequestBody SignUpRequest signupRequest) {
		ResponseEntity res = null;
		SignUpResponse signUpResponse = userService.userSignup(signupRequest);
		res = new ResponseEntity(signUpResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userSignin(@RequestBody SignUpRequest signupRequest) {
		ResponseEntity res = null;
		SigninResponse signinResponse = userService.userSignin(signupRequest);
		res = new ResponseEntity(signinResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "userPersonalInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userPersonalInfo(
			@RequestBody UserPersonalInfoRequest pinfoRequest) {
		ResponseEntity res = null;
		UserPersonalInfoResponse userPinfoResponse = userService
				.userPersonalInfo(pinfoRequest);
		res = new ResponseEntity(userPinfoResponse, HttpStatus.OK);
		return res;
	}
}
