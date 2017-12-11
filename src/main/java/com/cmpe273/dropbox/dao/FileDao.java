package com.cmpe273.dropbox.dao;

import org.springframework.data.repository.CrudRepository;

public interface FileDao extends CrudRepository<com.cmpe273.dropbox.model.Files, Long> {

}
