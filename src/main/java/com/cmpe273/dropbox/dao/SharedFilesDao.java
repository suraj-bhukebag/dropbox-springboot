package com.cmpe273.dropbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe273.dropbox.model.Files;
import com.cmpe273.dropbox.model.SharedFiles;

public interface SharedFilesDao extends CrudRepository<SharedFiles, Long> {
	
	@Query(value = "select * from shared_files where shared_by_id = ?1 or shared_with_id = ?1", nativeQuery = true)
	public List<SharedFiles> findBySharedByandSHaredWith(@Param("user_id") long user_id);	

}
