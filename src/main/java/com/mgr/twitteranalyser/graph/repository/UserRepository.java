package com.mgr.twitteranalyser.graph.repository;

import java.io.Serializable;
import com.mgr.twitteranalyser.graph.model.TwitterUser;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GraphRepository<TwitterUser>, Serializable {

    TwitterUser findByScreenName(String screenName);

}
