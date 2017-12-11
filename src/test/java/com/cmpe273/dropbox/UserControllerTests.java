package com.cmpe273.dropbox;

import java.util.Date;
import java.util.List;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.cmpe273.dropbox.mappers.SignUpRequest;
import com.cmpe273.dropbox.mappers.UserEducationInfoRequest;
import com.cmpe273.dropbox.mappers.UserInterestInfoRequest;
import com.cmpe273.dropbox.mappers.UserPersonalInfoRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DropboxApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTests {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void signup() throws ParseException {

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setEmail("aa@gmail.com");
		signUpRequest.setPassword("aa");
		signUpRequest.setFname("suraj");
		signUpRequest.setLname("Bhukebag");

		HttpEntity<SignUpRequest> entity = new HttpEntity<SignUpRequest>(
				signUpRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/signup"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());

		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertNotNull("User Signup Successfull.", jsonRes.get("msg"));

	}

	@Test
	public void signin() throws ParseException {

		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setEmail("suraj@gmail.com");
		signUpRequest.setPassword("suraj");

		HttpEntity<SignUpRequest> entity = new HttpEntity<SignUpRequest>(
				signUpRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/signin"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());

		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertEquals(true, jsonRes.get("loggedIn"));
		Assert.assertNotNull(jsonRes.get("user"));

	}

	@Test
	public void userPersonalInfo() throws ParseException {

		UserPersonalInfoRequest userPersonalInfoRequest = new UserPersonalInfoRequest();
		userPersonalInfoRequest.setEmail("suraj@gmail.com");
		userPersonalInfoRequest.setContact("1232456");
		userPersonalInfoRequest.setDob(new Date().getTime());

		HttpEntity<UserPersonalInfoRequest> entity = new HttpEntity<UserPersonalInfoRequest>(
				userPersonalInfoRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/userPersonalInfo"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());

		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertNotNull(jsonRes.get("pinfo"));

	}
	
	@Test
	public void userEduInfo() throws ParseException {

		UserEducationInfoRequest userPersonalInfoRequest = new UserEducationInfoRequest();
		userPersonalInfoRequest.setEmail("suraj@gmail.com");
		userPersonalInfoRequest.setCollegeName("SJSU");
		userPersonalInfoRequest.setGpa(3.5f);
		userPersonalInfoRequest.setMajor("SSE");
		userPersonalInfoRequest.setStartDate(new Date().getTime());
		userPersonalInfoRequest.setEndDate(new Date().getTime());

		HttpEntity<UserEducationInfoRequest> entity = new HttpEntity<UserEducationInfoRequest>(
				userPersonalInfoRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/userEduInfo"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());

		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertNotNull(jsonRes.get("eduinfo"));

	}

	@Test
	public void userIntInfo() throws ParseException {

		UserInterestInfoRequest userPersonalInfoRequest = new UserInterestInfoRequest();
		userPersonalInfoRequest.setComment("Adding new comment");
		userPersonalInfoRequest.setInterest("General");
		userPersonalInfoRequest.setUserId(1l);

		HttpEntity<UserInterestInfoRequest> entity = new HttpEntity<UserInterestInfoRequest>(
				userPersonalInfoRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/userIntInfo"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());

		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertNotNull(jsonRes.get("interests"));
		Assert.assertTrue(((List<String>) jsonRes.get("interests")).size() > 0 );

	}
}
