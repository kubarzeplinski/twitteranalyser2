package com.mgr.twitteranalyser.graph.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@NodeEntity
public class Keyword {

    @GraphId
    Long nodeId;
    @Getter
    String name;
    @Setter
    @Relationship(type = "INTERESTED_IN", direction = Relationship.INCOMING)
    private Tweet tweet;

    public Keyword(String name) {
        this.name = name;
    }

}
