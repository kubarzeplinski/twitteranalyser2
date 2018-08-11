package com.mgr.twitteranalyser.dashboard.repository;

import com.mgr.twitteranalyser.dashboard.model.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Long>, Serializable {

    Keyword findByName(String name);

}
