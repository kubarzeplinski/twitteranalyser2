package com.mgr.twitteranalyser.interestedinrelation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface InterestedInRelationRepository extends Neo4jRepository<InterestedInRelation, Long> {

    @Query("MATCH (keyword:Keyword)<-[relation:INTERESTED_IN]-(user:TwitterUser) " +
            "WHERE relation.createdAt IS NOT NULL " +
            "AND keyword.name = {keyword} " +
            "RETURN relation.createdAt + ' ' + relation.text " +
            "ORDER BY relation.createdAt DESC " +
            "LIMIT 5")
    Set<String> findLatest5Tweets(@Param("keyword") String keyword);

    @Query("MATCH (keyword:Keyword)<-[relation:INTERESTED_IN]-(user:TwitterUser) " +
            "WHERE keyword.name = {keyword} " +
            "AND user.screenName = {userName} " +
            "AND user.screenName IS NOT NULL " +
            "AND relation.createdAt IS NOT NULL " +
            "AND relation.language IS NOT NULL " +
            "AND relation.text IS NOT NULL " +
            "RETURN relation.createdAt " +
            "+ '!#!#!RELATION!#!#!' + relation.language " +
            "+ '!#!#!RELATION!#!#!' + relation.text " +
            "ORDER BY relation.createdAt DESC")
    List<String> findRelations(@Param("keyword") String keyword, @Param("userName") String userName);

}
