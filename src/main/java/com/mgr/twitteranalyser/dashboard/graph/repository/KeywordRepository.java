package com.mgr.twitteranalyser.dashboard.graph.repository;

import java.io.Serializable;
import org.springframework.data.neo4j.repository.GraphRepository;
import com.mgr.twitteranalyser.dashboard.graph.model.Keyword;

public interface KeywordRepository extends GraphRepository<Keyword>, Serializable {
}
