package com.cmpe273.dropbox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe273.dropbox.mappers.CreateFolderRequest;
import com.cmpe273.dropbox.mappers.FileUploadResponse;
import com.cmpe273.dropbox.mappers.GenericResponse;
import com.cmpe273.dropbox.mappers.ListDirRequest;
import com.cmpe273.dropbox.mappers.ListDirResponse;
import com.cmpe273.dropbox.mappers.StarFileRequest;
import com.cmpe273.dropbox.mappers.StarFilesResponse;
import com.cmpe273.dropbox.mappers.UserActivityResponse;
import com.cmpe273.dropbox.services.FileService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

	@Autowired
	FileService fileService;

	@PostMapping(path = "fileupload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userSignUp(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String email,
			@RequestParam("path") String path) {
		ResponseEntity res = null;
		FileUploadResponse fileUploadResponse = fileService.uploadFile(file,
				email, path);
		res = new ResponseEntity(fileUploadResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "listdir", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userFiles(
			@RequestBody ListDirRequest listDirRequest) {
		ResponseEntity res = null;
		ListDirResponse listDirResponse = fileService
				.getUserFileList(listDirRequest);
		res = new ResponseEntity(listDirResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "createFolder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createFolder(
			@RequestBody CreateFolderRequest createFolderRequest) {
		ResponseEntity res = null;
		GenericResponse createFolderResponse = fileService
				.createFolder(createFolderRequest);
		res = new ResponseEntity(createFolderResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "starFile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> starFile(
			@RequestBody StarFileRequest starFileRequest) {
		ResponseEntity res = null;
		GenericResponse createFolderResponse = fileService
				.starFile(starFileRequest);
		res = new ResponseEntity(createFolderResponse, HttpStatus.OK);
		return res;
	}

	@GetMapping(path = "starredFiles/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserStarredFiles(
			@PathVariable(value = "userId") long userId) {

		ResponseEntity res = null;
		StarFilesResponse starFilesResponse = fileService
				.getUserStarredFiles(userId);
		res = new ResponseEntity(starFilesResponse, HttpStatus.OK);
		return res;

	}

	@GetMapping(path = "userActivity/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserActivity(
			@PathVariable(value = "userId") long userId) {

		ResponseEntity res = null;
		UserActivityResponse userActivityResponse = fileService
				.getUserActivity(userId);
		res = new ResponseEntity(userActivityResponse, HttpStatus.OK);
		return res;

	}

}
