package com.mgr.twitteranalyser.dashboard.graph.repository;

import java.io.Serializable;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;
import com.mgr.twitteranalyser.dashboard.graph.model.TwitterUser;

@Repository
public interface TwitterUserRepository extends GraphRepository<TwitterUser>, Serializable {

    TwitterUser findById(long id);

    Iterable<TwitterUser> findAll();

}
