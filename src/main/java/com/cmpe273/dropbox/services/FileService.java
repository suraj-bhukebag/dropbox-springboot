package com.cmpe273.dropbox.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe273.dropbox.dao.FileDao;
import com.cmpe273.dropbox.dao.UserDao;
import com.cmpe273.dropbox.mappers.FileUploadResponse;

@Service
public class FileService {
	
	private static String UPLOADED_FOLDER = "files/";
	
	@Autowired
	FileDao fileDao;
	
	@Autowired
	UserDao userDao;
	
	public FileUploadResponse uploadFile(MultipartFile file, String email, String path) {
		
		FileUploadResponse fileUploadResponse = new FileUploadResponse();
		
		try {
			byte[] bytes = file.getBytes();
			Path localPath = Paths.get(UPLOADED_FOLDER + email + "/"+path+"/"
					+ file.getOriginalFilename());
			Files.write(localPath, bytes);
			
			com.cmpe273.dropbox.model.Files f = new com.cmpe273.dropbox.model.Files();
			f.setCreatedBy(userDao.findUserByEmail(email));
			f.setDateCreated(new Date().getTime());
			f.setDirectory(false);
			f.setName(file.getOriginalFilename());
			f.setPath(path);
			f.setStarred(false);
			f.setLink(getRandomHexString(20));
			
			fileDao.save(f);
			fileUploadResponse.setCode(200);
			fileUploadResponse.setMsg("File Uploaded");
			

		} catch (IOException e) {
			fileUploadResponse.setCode(500);
			fileUploadResponse.setMsg("File Upload Failed");
		}

		
		return fileUploadResponse;
		
	}
	
	private String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

}
