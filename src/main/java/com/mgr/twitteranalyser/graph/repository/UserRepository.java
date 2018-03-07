package com.mgr.twitteranalyser.graph.repository;

import com.mgr.twitteranalyser.graph.model.User;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User> {

    User findByScreenName(String screenName);

}
