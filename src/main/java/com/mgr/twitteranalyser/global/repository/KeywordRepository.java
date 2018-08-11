package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Long>, Serializable {

    Keyword findByName(String name);

}
