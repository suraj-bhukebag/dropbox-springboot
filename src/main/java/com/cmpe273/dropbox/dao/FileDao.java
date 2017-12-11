package com.cmpe273.dropbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe273.dropbox.model.Files;

public interface FileDao extends CrudRepository<com.cmpe273.dropbox.model.Files, Long> {
	
	@Query(value = "SELECT * FROM files WHERE created_by_id = ?1 and path = ?2", nativeQuery = true)
	public List<Files> findByCreatedByAndPath(@Param("created_by_id") long created_by_id, @Param("path") String path);
	
	@Query(value = "SELECT * FROM files WHERE created_by_id = ?1 and path = ?2 and name = ?3", nativeQuery = true)
	public Files findByCreatedByAndPathAndName(@Param("created_by_id") long created_by_id, @Param("path") String path, @Param("name") String name);
	
	@Query(value = "SELECT * FROM files WHERE created_by_id = ?1 and is_starred = ?2", nativeQuery = true)
	public List<Files> findByCreatedByAndStarred(@Param("created_by_id") long created_by_id, @Param("is_starred") boolean is_starred);


}
