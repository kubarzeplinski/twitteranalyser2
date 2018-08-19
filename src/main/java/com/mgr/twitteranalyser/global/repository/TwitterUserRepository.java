package com.mgr.twitteranalyser.global.repository;

import com.mgr.twitteranalyser.global.model.TwitterUser;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TwitterUserRepository extends PagingAndSortingRepository<TwitterUser, Long>, Serializable {

    TwitterUser findByUserId(long userId);

    @Query("MATCH (n) " +
            "WHERE n.lastKeyword = {lastKeyword} AND n.createdAt IS NOT NULL " +
            "RETURN n " +
            "ORDER BY n.createdAt DESC " +
            "LIMIT 5")
    List<TwitterUser> findTop5ByLastKeywordOrderByCreatedAtDesc(@Param("lastKeyword") String lastKeyword);

    Long countByLastKeyword(String lastKeyword);

    @Query("MATCH (n) " +
            "WHERE n.lastKeyword = {lastKeyword} AND n.followersCount IS NOT NULL " +
            "RETURN n " +
            "ORDER BY n.followersCount DESC " +
            "LIMIT 5")
    List<TwitterUser> findTop5ByLastKeywordOrderByFollowersCountDesc(@Param("lastKeyword") String lastKeyword);

    @Query("MATCH (n) " +
            "WHERE n.lastKeyword = {lastKeyword} AND n.location IS NOT NULL " +
            "RETURN DISTINCT n.location " +
            "ORDER BY n.location ASC " +
            "LIMIT 5")
    List<String> findDistinctTop5ByLastKeywordOrderByLocationAsc(@Param("lastKeyword") String lastKeyword);

    @Query("MATCH (t:TwitterUser)-[r:INTERESTED_IN_RELATIONS]->(k:Keyword) WHERE k.name = {keyword} RETURN t")
    Stream<TwitterUser> findAllByKeyword(@Param("keyword") String keyword);

}
