package com.cmpe273.dropbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe273.dropbox.model.FileActivity;

public interface FileActivityDao extends CrudRepository<FileActivity, Long> {
	
	@Query(value = "SELECT * FROM file_activity WHERE file_id = ?1", nativeQuery = true)
	public FileActivity findByFile(@Param("file_id") long file_id);
	
	@Query(value = "SELECT * FROM file_activity WHERE user_id = ?1 order by date_created desc limit 7", nativeQuery = true)
	public List<FileActivity> findByUser(@Param("user_id") long user_id);
	

}
