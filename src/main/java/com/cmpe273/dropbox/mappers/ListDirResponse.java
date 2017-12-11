package com.cmpe273.dropbox.mappers;

import java.util.List;

import com.cmpe273.dropbox.model.Files;

public class ListDirResponse extends GenericResponse {
	
	private List<Files> files;

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}
	
	

}
