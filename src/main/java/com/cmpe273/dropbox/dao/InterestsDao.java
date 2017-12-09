package com.cmpe273.dropbox.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cmpe273.dropbox.model.Interests;

public interface InterestsDao extends CrudRepository<Interests, Long> {

	@Query(value = "SELECT * FROM interests WHERE user_id = ?1", nativeQuery = true)
	public List<Interests> findInterestsByUser(@Param("user_id") long user_id);

}
