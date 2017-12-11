package com.cmpe273.dropbox.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe273.dropbox.dao.FileActivityDao;
import com.cmpe273.dropbox.dao.FileDao;
import com.cmpe273.dropbox.dao.SharedFilesDao;
import com.cmpe273.dropbox.dao.UserDao;
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
import com.cmpe273.dropbox.mappers.SharedFile;
import com.cmpe273.dropbox.mappers.StarFileRequest;
import com.cmpe273.dropbox.mappers.StarFilesResponse;
import com.cmpe273.dropbox.mappers.UserActivityResponse;
import com.cmpe273.dropbox.model.FileActivity;
import com.cmpe273.dropbox.model.SharedFiles;
import com.cmpe273.dropbox.model.User;

@Service
public class FileService {

	private static String UPLOADED_FOLDER = "files/";

	@Autowired
	private FileDao fileDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SharedFilesDao sharedFilesDao;
	
	@Autowired
	private FileActivityDao fileActivityDao;

	public FileUploadResponse uploadFile(MultipartFile file, String email,
			String path) {

		FileUploadResponse fileUploadResponse = new FileUploadResponse();

		try {
			byte[] bytes = file.getBytes();
			Path localPath = Paths.get(UPLOADED_FOLDER + email + "/" + path
					+ "/" + file.getOriginalFilename());
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

	private String getRandomHexString(int numchars) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < numchars) {
			sb.append(Integer.toHexString(r.nextInt()));
		}

		return sb.toString().substring(0, numchars);
	}

	public ListDirResponse getUserFileList(ListDirRequest listDirRequest) {

		if (!listDirRequest.getDir().equals("/")
				&& listDirRequest.getDir().lastIndexOf("/") == 0) {
			String name = listDirRequest.getDir().substring(1);
			com.cmpe273.dropbox.model.Files file = fileDao
					.findByCreatedByAndPathAndName(listDirRequest.getId(), "/",
							name);
			FileActivity fileActivity = fileActivityDao
					.findByFile(file.getId());
			if (fileActivity == null) {
				fileActivity = new FileActivity();
				fileActivity.setFile(file);
				fileActivity.setUser(userDao.findOne(listDirRequest.getId()));
			}
			fileActivity.setDateCreated(new Date().getTime());
			fileActivityDao.save(fileActivity);

		}

		List<com.cmpe273.dropbox.model.Files> files = fileDao
				.findByCreatedByAndPath(listDirRequest.getId(),
						listDirRequest.getDir());

		for (com.cmpe273.dropbox.model.Files f : files) {
			if (f.getPath().equals("/")) {
				f.setPath(f.getPath() + f.getName());
			} else {
				f.setPath(f.getPath() + "/" + f.getName());
			}
		}

		ListDirResponse listDirResponse = new ListDirResponse();
		listDirResponse.setCode(200);
		listDirResponse.setMsg("File List");
		listDirResponse.setFiles(files);

		return listDirResponse;
	}

	public GenericResponse createFolder(CreateFolderRequest createFolderRequest) {

		User user = userDao.findUserByEmail(createFolderRequest.getEmail());
		com.cmpe273.dropbox.model.Files folder = new com.cmpe273.dropbox.model.Files();
		folder.setCreatedBy(user);
		folder.setDateCreated(new Date().getTime());
		folder.setDirectory(true);
		folder.setStarred(false);
		folder.setName(createFolderRequest.getFolderName());
		folder.setPath(createFolderRequest.getPath());
		fileDao.save(folder);

		Path path = Paths.get("files\\" + createFolderRequest.getEmail() + "\\"
				+ createFolderRequest.getPath() + "\\"
				+ createFolderRequest.getFolderName());
		GenericResponse createFolderResponse = new GenericResponse();
		try {
			Files.createDirectories(path);
			createFolderResponse.setCode(200);
			createFolderResponse.setMsg("New Folder Created");
		} catch (IOException e) {
			createFolderResponse.setCode(500);
			createFolderResponse.setMsg("New Folder Creation failed");
		}
		return createFolderResponse;
	}

	public GenericResponse starFile(StarFileRequest starFileRequest) {

		String p = starFileRequest.getPath();
		int index = p.lastIndexOf("/");
		String path = "";
		boolean isStarred = false;
		if (starFileRequest.getStar().equalsIgnoreCase("star")) {
			isStarred = true;
		}
		if (index == 0) {
			path = "/";
		} else {
			path = p.substring(0, index);
		}
		String name = p.substring(index + 1);

		com.cmpe273.dropbox.model.Files file = fileDao
				.findByCreatedByAndPathAndName(starFileRequest.getId(), path,
						name);

		if (isStarred) {
			file.setStarred(true);
		} else {
			file.setStarred(false);
		}
		com.cmpe273.dropbox.model.Files updatedFile = fileDao.save(file);

		FileActivity fileActivity = fileActivityDao.findByFile(updatedFile
				.getId());
		if (fileActivity == null) {
			fileActivity = new FileActivity();
			fileActivity.setFile(updatedFile);
			fileActivity.setUser(updatedFile.getCreatedBy());
		}
		fileActivity.setDateCreated(new Date().getTime());
		fileActivityDao.save(fileActivity);

		GenericResponse starResponse = new GenericResponse();
		starResponse.setCode(200);
		if (isStarred) {
			starResponse.setMsg("File/Folder Starred");
		} else {
			starResponse.setMsg("File/Folder Unstarred");
		}
		return starResponse;
	}

	public StarFilesResponse getUserStarredFiles(long userId) {

		List<com.cmpe273.dropbox.model.Files> files = fileDao
				.findByCreatedByAndStarred(userId, true);

		for (com.cmpe273.dropbox.model.Files f : files) {
			if (f.getPath().equals("/")) {
				f.setPath(f.getPath() + f.getName());
			} else {
				f.setPath(f.getPath() + "/" + f.getName());
			}
		}

		StarFilesResponse starFilesResponse = new StarFilesResponse();
		starFilesResponse.setCode(200);
		starFilesResponse.setMsg("Starred Files");
		starFilesResponse.setStarred(files);

		return starFilesResponse;
	}

	public UserActivityResponse getUserActivity(long userId) {

		List<FileActivity> fileActivities = fileActivityDao.findByUser(userId);
		List<com.cmpe273.dropbox.model.Files> activity = new ArrayList<com.cmpe273.dropbox.model.Files>();
		for (FileActivity fileActivity : fileActivities) {
			com.cmpe273.dropbox.model.Files f = fileActivity.getFile();
			if (f.getPath().equals("/")) {
				f.setPath(f.getPath() + f.getName());
			} else {
				f.setPath(f.getPath() + "/" + f.getName());
			}
			activity.add(f);
		}
		UserActivityResponse userActivityResponse = new UserActivityResponse();
		userActivityResponse.setActivity(activity);
		userActivityResponse.setCode(200);
		userActivityResponse.setMsg("User Activity");
		return userActivityResponse;
	}

	@Transactional
	public GenericResponse deleteFileFolder(
			DeleteFileFolderRequest deleteFileFolderRequest) {

		String path = deleteFileFolderRequest.getPath();
		String email = deleteFileFolderRequest.getEmail();
		boolean isDirectory = deleteFileFolderRequest.getDirectory();
		long userId = deleteFileFolderRequest.getId();

		int index = path.lastIndexOf("/");
		String p = "";
		if (index == 0) {
			p = "/";
		} else {
			p = path.substring(0, index);
		}
		String name = path.substring(index + 1);
		System.out.println("User : " + userId + "   Path : " + p + "   Name :"
				+ name + " Is isDirectory : " + isDirectory);
		fileDao.deleteByCreatedByAndPathAndName(userId, p, name);

		String filePath = "files\\" + email + "\\" + path;
		GenericResponse genericResponse = new GenericResponse();

		try {
			if (isDirectory) {
				FileUtils.deleteDirectory(new File(path));
				genericResponse.setMsg("Folder Deleted Successfully.");
			} else {
				Files.delete(Paths.get(filePath));
				genericResponse.setMsg("File Deleted Successfully.");

			}
			genericResponse.setCode(200);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			genericResponse.setCode(500);
			genericResponse.setMsg("File/Folder Deletion Failed");
		}

		return genericResponse;
	}

	public String downloadFile(String link) {

		com.cmpe273.dropbox.model.Files file = fileDao.findByLink(link);
		FileActivity fileActivity = fileActivityDao.findByFile(file.getId());
		if (fileActivity == null) {
			fileActivity = new FileActivity();
			fileActivity.setFile(file);
			fileActivity.setUser(file.getCreatedBy());
		}
		fileActivity.setDateCreated(new Date().getTime());
		fileActivityDao.save(fileActivity);

		String filepath = "files\\" + file.getCreatedBy().getEmail() + "\\"
				+ file.getPath() + "/" + file.getName();
		if (file.getPath().equals("/")) {
			filepath = "files\\" + file.getCreatedBy().getEmail() + "\\"
					+ file.getPath() + file.getName();
		}
		return filepath;
	}

	public DownloadLinkResponse getDownloadLink(
			DownloadLinkRequest downloadLinkRequest) {

		String email = downloadLinkRequest.getEmail();
		String p = downloadLinkRequest.getPath();
		int index = p.lastIndexOf("/");
		String path = "";
		if (index == 0) {
			path = "/";
		} else {
			path = p.substring(0, index);
		}
		String name = p.substring(index + 1);

		com.cmpe273.dropbox.model.Files file = fileDao
				.findByCreatedByAndPathAndName(userDao.findUserByEmail(email)
						.getId(), path, name);
		DownloadLinkResponse downloadLinkResponse = new DownloadLinkResponse();
		downloadLinkResponse.setCode(200);
		downloadLinkResponse.setMsg("Download Link");
		downloadLinkResponse.setLink("http://localhost:3001/filedownload/"
				+ file.getLink());

		return downloadLinkResponse;
	}

	public GenericResponse shareFile(ShareFileRequest shareFileRequest) {

		String email = shareFileRequest.getEmail();
		String p = shareFileRequest.getPath();
		int index = p.lastIndexOf("/");
		String path = "";
		if (index == 0) {
			path = "/";
		} else {
			path = p.substring(0, index);
		}
		List<String> sharedWithList = new ArrayList<String>();
		String name = p.substring(index + 1);
		String shareListString = shareFileRequest.getSharedWith();
		if (shareListString.contains(",")) {
			String[] splitArr = shareListString.split(",");
			for (int j = 0; j < splitArr.length; j++) {
				sharedWithList.add(splitArr[j].trim());
			}
		} else {
			sharedWithList.add(shareListString.trim());
		}

		User user = userDao.findUserByEmail(email);
		com.cmpe273.dropbox.model.Files file = fileDao
				.findByCreatedByAndPathAndName(user.getId(), path, name);

		for (String sharedUser : sharedWithList) {
			User sUser = userDao.findUserByEmail(sharedUser);
			SharedFiles sharedFiles = new SharedFiles();
			sharedFiles.setFile(file);
			sharedFiles.setSharedBy(user);
			sharedFiles.setSharedWith(sUser);
			
			sharedFilesDao.save(sharedFiles);
			
			FileActivity fileActivity = fileActivityDao.findByFile(file
					.getId());
			if (fileActivity == null) {
				fileActivity = new FileActivity();
				fileActivity.setFile(file);
				fileActivity.setUser(file.getCreatedBy());
			}
			fileActivity.setDateCreated(new Date().getTime());
			fileActivityDao.save(fileActivity);
			
		}
		
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setCode(200);
		genericResponse.setMsg("File/Folder Shared");
		return genericResponse;
	}

	public SHaredFileResponse sharedFiles(String email) {
		
		User user = userDao.findUserByEmail(email);
		List<SharedFiles> sharedFiles = sharedFilesDao.findBySharedByandSHaredWith(user.getId());
		SHaredFileResponse sHaredFileResponse = new SHaredFileResponse();
		List<SharedFile> files = new ArrayList<SharedFile>();
		List<SharedFile> folders = new ArrayList<SharedFile>();
		for(SharedFiles sf : sharedFiles) {
			SharedFile sharedFile = new SharedFile();
			sharedFile.setName(sf.getFile().getName());
			sharedFile.setPath(sf.getFile().getPath());
			sharedFile.setOwner(sf.getFile().getCreatedBy().getId());
			sharedFile.setStarred(sf.getFile().isStarred());
			if(sf.getFile().isDirectory()) {
				sharedFile.setDirectory(true);
				folders.add(sharedFile);
			}
			else {
				sharedFile.setLink(sf.getFile().getLink());
				sharedFile.setDirectory(false);
				files.add(sharedFile);
			}
		}
		sHaredFileResponse.setFiles(files);
		sHaredFileResponse.setFolders(folders);
		sHaredFileResponse.setCode(200);
		sHaredFileResponse.setMsg("Shared FIles");
		return sHaredFileResponse;
	}

}
