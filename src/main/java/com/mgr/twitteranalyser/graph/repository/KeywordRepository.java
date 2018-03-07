package com.mgr.twitteranalyser.graph.repository;

import org.springframework.data.neo4j.repository.GraphRepository;
import com.mgr.twitteranalyser.graph.model.Keyword;

public interface KeywordRepository extends GraphRepository<Keyword> {
}
