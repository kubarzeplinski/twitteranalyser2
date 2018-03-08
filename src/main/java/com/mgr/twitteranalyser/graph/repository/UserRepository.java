package com.mgr.twitteranalyser.graph.repository;

import java.io.Serializable;
import com.mgr.twitteranalyser.graph.model.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GraphRepository<User>, Serializable {

    User findByScreenName(String screenName);

}
