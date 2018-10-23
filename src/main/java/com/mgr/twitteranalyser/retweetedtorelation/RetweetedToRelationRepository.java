package com.mgr.twitteranalyser.retweetedtorelation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RetweetedToRelationRepository extends Neo4jRepository<RetweetedToRelation, Long> {

    @Query("MATCH (keyword:Keyword)<-[relation1:INTERESTED_IN]-(user:TwitterUser)<-[relation2:RETWEETED_TO]-(retweeter:TwitterUser) " +
            "WHERE relation2.createdAt IS NOT NULL " +
            "AND keyword.name = {keyword} " +
            "RETURN relation2.createdAt + ' ' + relation2.text " +
            "ORDER BY relation2.createdAt DESC " +
            "LIMIT 5")
    Set<String> findLatest5(@Param("keyword") String keyword);

}
