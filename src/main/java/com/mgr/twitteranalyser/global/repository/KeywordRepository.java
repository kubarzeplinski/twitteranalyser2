package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.Keyword;
import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;
import java.util.stream.Stream;

public interface KeywordRepository extends CrudRepository<Keyword, Long>, Serializable {

    Keyword findByName(String name);

    Stream<Keyword> readAllByNameNotNull();

}
