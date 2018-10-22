package com.mgr.twitteranalyser.keyword;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.io.Serializable;
import java.util.Optional;
import java.util.stream.Stream;

public interface KeywordRepository extends Neo4jRepository<Keyword, Long>, Serializable {

    Optional<Keyword> findByName(String name);

    Stream<Keyword> readAllByNameNotNull();

}
