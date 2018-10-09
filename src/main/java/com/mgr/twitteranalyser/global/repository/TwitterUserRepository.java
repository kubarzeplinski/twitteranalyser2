package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.TwitterUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface TwitterUserRepository extends Neo4jRepository<TwitterUser, Long>, Serializable {

    TwitterUser findByUserId(long userId);

    Optional<TwitterUser> findByScreenName(String screenName);

    @Query("MATCH (keyword:Keyword)<-[relations:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE ALL(rel IN relations WHERE rel.createdAtLocalDate IS NOT NULL) " +
            "AND keyword.name = {keyword} " +
            "RETURN user, relations, keyword " +
            "ORDER BY user.createdAtLocalDate " +
            "LIMIT 5")
    List<TwitterUser> findTop5CreatedByKeyword(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN COUNT(user)")
    Long countByKeyword(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.followersCount IS NOT NULL " +
            "RETURN user " +
            "ORDER BY user.followersCount DESC " +
            "LIMIT 5")
    List<TwitterUser> findTop5ByKeywordOrderByFollowersCountDesc(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:RETWEETED_TO|:INTERESTED_IN*]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.location IS NOT NULL " +
            "RETURN DISTINCT user.location " +
            "ORDER BY user.location ASC " +
            "LIMIT 5"
    )
    List<String> findDistinctTop5ByKeywordOrderByLocationAsc(@Param("keyword") String keyword);

    @Query("MATCH (user:TwitterUser)<-[relation:INTERESTED_IN]->(keyword:Keyword) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN user")
    Stream<TwitterUser> findAllInterestedInByKeyword(@Param("keyword") String keyword);

    @Query("MATCH (retweeter:TwitterUser)-[relation1:RETWEETED_TO]->(user:TwitterUser)-[relation2:INTERESTED_IN]->(keyword:Keyword) " +
            "WHERE keyword.name = {keyword} " +
            "RETURN retweeter, relation1")
    Stream<TwitterUser> findAllRetweetedToByKeyword(@Param("keyword") String keyword);

}
