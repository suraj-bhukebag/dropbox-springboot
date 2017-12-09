package com.cmpe273.dropbox.dao;

import org.springframework.data.repository.CrudRepository;

import com.cmpe273.dropbox.model.PersonalInfo;

public interface PersonalInfoDao extends CrudRepository<PersonalInfo, Long> {

}
