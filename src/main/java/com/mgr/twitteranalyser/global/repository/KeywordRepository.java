package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.Keyword;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.io.Serializable;
import java.util.stream.Stream;

public interface KeywordRepository extends Neo4jRepository<Keyword, Long>, Serializable {

    Keyword findByName(String name);

    Stream<Keyword> readAllByNameNotNull();

}
