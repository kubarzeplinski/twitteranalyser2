package com.mgr.twitteranalyser.twitteruser;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface TwitterUserRepository extends Neo4jRepository<TwitterUser, Long>, Serializable {

    TwitterUser findByUserId(long userId);

    Optional<TwitterUser> findByScreenName(String screenName);

    @Query("MATCH (keyword:Keyword)<-[relations:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE ALL(rel IN relations WHERE rel.createdAt IS NOT NULL) " +
            "AND keyword.name = {keyword} " +
            "RETURN user, relations, keyword " +
            "ORDER BY user.createdAt " +
            "LIMIT 5")
    Stream<TwitterUser> findLatest5(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN COUNT(user)")
    Long getCount(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.followersCount IS NOT NULL " +
            "RETURN DISTINCT user " +
            "ORDER BY user.followersCount DESC " +
            "LIMIT 5")
    Stream<TwitterUser> findTop5ByFollowers(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.location IS NOT NULL " +
            "RETURN DISTINCT user.location " +
            "ORDER BY user.location ASC " +
            "LIMIT 5")
    Set<String> findTop5Locations(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:INTERESTED_IN]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN DISTINCT user")
    Stream<TwitterUser> findAllInterestedIn(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation1:INTERESTED_IN]-(user:TwitterUser)<-[relation2:RETWEETED_TO]-(retweeter:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN retweeter, relation2")
    Stream<TwitterUser> findAllRetweetedTo(@Param("keyword") String keyword);

}
