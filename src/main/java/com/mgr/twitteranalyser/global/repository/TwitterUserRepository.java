package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.TwitterUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

public interface TwitterUserRepository extends PagingAndSortingRepository<TwitterUser, Long>, Serializable {

    TwitterUser findByUserId(long userId);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) " +
            "WHERE r.createdAt IS NOT NULL " +
            "AND k.name = {keyword} " +
            "RETURN t " +
            "ORDER BY r.createdAt DESC " +
            "LIMIT 5")
    List<TwitterUser> findTop5ByKeywordOrderByCreatedAtDesc(@Param("keyword") String keyword);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) " +
            "WHERE k.name = {keyword} " +
            "RETURN COUNT(t)")
    Long countByKeyword(@Param("keyword") String keyword);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) " +
            "WHERE k.name = {keyword} " +
            "AND t.followersCount IS NOT NULL " +
            "RETURN t " +
            "ORDER BY t.followersCount DESC " +
            "LIMIT 5")
    List<TwitterUser> findTop5ByKeywordOrderByFollowersCountDesc(@Param("keyword") String keyword);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) " +
            "WHERE k.name = {keyword} " +
            "AND t.location IS NOT NULL " +
            "RETURN DISTINCT t.location " +
            "ORDER BY t.location ASC " +
            "LIMIT 5")
    List<String> findDistinctTop5ByKeywordOrderByLocationAsc(@Param("keyword") String keyword);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) WHERE k.name = {keyword} RETURN t")
    Stream<TwitterUser> findAllByKeyword(@Param("keyword") String keyword);

}
