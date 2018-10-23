package com.mgr.twitteranalyser.interestedinrelation;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface InterestedInRelationRepository extends Neo4jRepository<InterestedInRelation, Long> {

    @Query("MATCH (keyword:Keyword)<-[relation:INTERESTED_IN]-(user:TwitterUser) " +
            "WHERE relation.createdAt IS NOT NULL " +
            "AND keyword.name = {keyword} " +
            "RETURN relation.createdAt + ' ' + relation.text " +
            "ORDER BY relation.createdAt DESC " +
            "LIMIT 5")
    Set<String> findLatest5(@Param("keyword") String keyword);

}
