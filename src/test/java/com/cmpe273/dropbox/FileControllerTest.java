package com.cmpe273.dropbox;

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

import com.cmpe273.dropbox.mappers.CreateFolderRequest;
import com.cmpe273.dropbox.mappers.DownloadLinkRequest;
import com.cmpe273.dropbox.mappers.ListDirRequest;
import com.cmpe273.dropbox.mappers.StarFileRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DropboxApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileControllerTest {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	public void listdir() throws ParseException {

		ListDirRequest listDirRequest = new ListDirRequest();
		listDirRequest.setDir("/");
		listDirRequest.setId(2l);

		HttpEntity<ListDirRequest> entity = new HttpEntity<ListDirRequest>(
				listDirRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/listdir"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertNotNull(jsonRes.get("files"));
		Assert.assertTrue(((List<String>) jsonRes.get("files")).size() > 0);

	}

	@Test
	public void createFolder() throws ParseException {

		CreateFolderRequest createFolderRequest = new CreateFolderRequest();
		createFolderRequest.setEmail("suraj@gmail.com");
		createFolderRequest.setFolderName("test");
		createFolderRequest.setPath("/");

		HttpEntity<CreateFolderRequest> entity = new HttpEntity<CreateFolderRequest>(
				createFolderRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/createFolder"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertEquals("New Folder Created", jsonRes.get("msg"));

	}

	@Test
	public void star() throws ParseException {

		StarFileRequest starFileRequest = new StarFileRequest();
		starFileRequest.setId(2l);
		starFileRequest.setPath("/PaymentInstruction.pdf");
		starFileRequest.setStar("star");
		HttpEntity<StarFileRequest> entity = new HttpEntity<StarFileRequest>(
				starFileRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/starFile"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertEquals("File/Folder Starred", jsonRes.get("msg"));

	}

	@Test
	public void unstar() throws ParseException {

		StarFileRequest starFileRequest = new StarFileRequest();
		starFileRequest.setId(2l);
		starFileRequest.setPath("/PaymentInstruction.pdf");
		starFileRequest.setStar("unstar");
		HttpEntity<StarFileRequest> entity = new HttpEntity<StarFileRequest>(
				starFileRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/starFile"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertEquals("File/Folder Unstarred", jsonRes.get("msg"));

	}

	@Test
	public void getStarredFiles() throws ParseException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/starredFiles/2"), HttpMethod.GET, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertTrue(((List<Object>) jsonRes.get("starred")).size() > 0);

	}

	@Test
	public void getUserActivity() throws ParseException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/userActivity/2"), HttpMethod.GET, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertTrue(((List<Object>) jsonRes.get("activity")).size() > 0);

	}

	@Test
	public void getLink() throws ParseException {

		DownloadLinkRequest downloadLinkRequest = new DownloadLinkRequest();
		downloadLinkRequest.setEmail("sa@gmail.com");
		downloadLinkRequest.setPath("/PaymentInstruction.pdf");
		HttpEntity<DownloadLinkRequest> entity = new HttpEntity<DownloadLinkRequest>(
				downloadLinkRequest, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/getDownloadLink"), HttpMethod.POST, entity,
				String.class);

		JSONParser parser = new JSONParser();
		JSONObject jsonRes = (JSONObject) parser.parse(response.getBody());
		Assert.assertEquals(200, jsonRes.get("code"));
		Assert.assertEquals("http://localhost:3001/filedownload/852d442a9d17ff0320e9", jsonRes.get("link"));

	}

}
