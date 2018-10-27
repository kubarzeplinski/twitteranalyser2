package com.mgr.twitteranalyser.retweetedtorelation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RetweetedToRelationRepository extends Neo4jRepository<RetweetedToRelation, Long> {

    @Query("MATCH (keyword:Keyword)<-[relation1:INTERESTED_IN]-(user:TwitterUser)<-[relation2:RETWEETED_TO]-(retweeter:TwitterUser) " +
            "WHERE relation2.createdAt IS NOT NULL " +
            "AND keyword.name = {keyword} " +
            "RETURN relation2.createdAt + ' ' + relation2.text " +
            "ORDER BY relation2.createdAt DESC " +
            "LIMIT 5")
    Set<String> findLatest5Tweets(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation1:INTERESTED_IN]-(user:TwitterUser)<-[relation2:RETWEETED_TO]-(retweeter:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.screenName = {userName} " +
            "AND retweeter.screenName = {retweeterName} " +
            "AND user.screenName IS NOT NULL " +
            "AND retweeter.screenName IS NOT NULL " +
            "AND relation2.createdAt IS NOT NULL " +
            "AND relation2.language IS NOT NULL " +
            "AND relation2.text IS NOT NULL " +
            "RETURN relation2.createdAt " +
            "+ '!#!#!RELATION!#!#!' + relation2.language " +
            "+ '!#!#!RELATION!#!#!' + relation2.text " +
            "ORDER BY relation2.createdAt DESC")
    List<String> findRelations(@Param("keyword") String keyword,
                               @Param("userName") String userName,
                               @Param("retweeterName") String retweeterName);

}
