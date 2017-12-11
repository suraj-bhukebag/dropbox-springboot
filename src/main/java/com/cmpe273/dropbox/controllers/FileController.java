package com.cmpe273.dropbox.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe273.dropbox.mappers.CreateFolderRequest;
import com.cmpe273.dropbox.mappers.DeleteFileFolderRequest;
import com.cmpe273.dropbox.mappers.DownloadLinkRequest;
import com.cmpe273.dropbox.mappers.DownloadLinkResponse;
import com.cmpe273.dropbox.mappers.FileUploadResponse;
import com.cmpe273.dropbox.mappers.GenericResponse;
import com.cmpe273.dropbox.mappers.ListDirRequest;
import com.cmpe273.dropbox.mappers.ListDirResponse;
import com.cmpe273.dropbox.mappers.SHaredFileResponse;
import com.cmpe273.dropbox.mappers.ShareFileRequest;
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

	@PostMapping(path = "getDownloadLink", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDownloadLink(
			@RequestBody DownloadLinkRequest downloadLinkRequest) {
		ResponseEntity res = null;
		DownloadLinkResponse downloadLinkResponse = fileService
				.getDownloadLink(downloadLinkRequest);
		res = new ResponseEntity(downloadLinkResponse, HttpStatus.OK);
		return res;
	}

	@PostMapping(path = "fileFolderDelete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteFileFolder(
			@RequestBody DeleteFileFolderRequest deleteFileFolderRequest) {
		ResponseEntity res = null;
		GenericResponse createFolderResponse = fileService
				.deleteFileFolder(deleteFileFolderRequest);
		res = new ResponseEntity(createFolderResponse, HttpStatus.OK);
		return res;
	}

	@RequestMapping(path = "filedownload/{link}", method = RequestMethod.GET)
	public void download(@PathVariable(value = "link") String link,
			HttpServletResponse response) throws IOException {

		String filepath = fileService.downloadFile(link);
		Path path = Paths.get(filepath);
		File file = new File(filepath);
		InputStream is = new FileInputStream(file);
		org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
		response.setContentType(Files.probeContentType(path));
		response.flushBuffer();

	}
	
	@PostMapping(path = "generateLink", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateLink(
			@RequestBody DownloadLinkRequest generateLinkRequest) {
		ResponseEntity res = null;
		DownloadLinkResponse downloadLinkResponse = fileService
				.getDownloadLink(generateLinkRequest);
		res = new ResponseEntity(downloadLinkResponse, HttpStatus.OK);
		return res;
	}
	
	
	@PostMapping(path = "share", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> shareFile(
			@RequestBody ShareFileRequest shareFileRequest) {
		ResponseEntity res = null;
		GenericResponse shareFileResponse = fileService
				.shareFile(shareFileRequest);
		res = new ResponseEntity(shareFileResponse, HttpStatus.OK);
		return res;
	}
	
	@PostMapping(path = "sharedFiles", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> shareFiles(
			@RequestBody ShareFileRequest shareFileRequest) {
		ResponseEntity res = null;
		SHaredFileResponse shareFileResponse = fileService
				.sharedFiles(shareFileRequest.getEmail());
		res = new ResponseEntity(shareFileResponse, HttpStatus.OK);
		return res;
	}
	
	

}
