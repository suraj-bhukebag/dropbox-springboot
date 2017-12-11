package com.cmpe273.dropbox.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe273.dropbox.mappers.FileUploadResponse;
import com.cmpe273.dropbox.mappers.GenericResponse;
import com.cmpe273.dropbox.services.FileService;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

	@Autowired
	FileService fileService;
	
	@PostMapping(path = "fileupload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userSignUp(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String email, @RequestParam("path") String path) {
		ResponseEntity res = null;
		FileUploadResponse fileUploadResponse = fileService.uploadFile(file, email, path);		
		res = new ResponseEntity(fileUploadResponse, HttpStatus.OK);
		return res;
	}

}
