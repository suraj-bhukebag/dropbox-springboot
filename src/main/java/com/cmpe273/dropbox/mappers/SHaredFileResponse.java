package com.cmpe273.dropbox.mappers;

import java.util.List;

public class SHaredFileResponse extends GenericResponse {

	private List<SharedFile> files;
	
	private List<SharedFile> folders;

	public List<SharedFile> getFiles() {
		return files;
	}

	public void setFiles(List<SharedFile> files) {
		this.files = files;
	}

	public List<SharedFile> getFolders() {
		return folders;
	}

	public void setFolders(List<SharedFile> folders) {
		this.folders = folders;
	}
	
	
}
