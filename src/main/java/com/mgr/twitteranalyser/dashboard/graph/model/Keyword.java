package com.mgr.twitteranalyser.dashboard.graph.model;

import java.io.Serializable;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NodeEntity
@NoArgsConstructor
@Getter
public class Keyword implements Serializable {

    @GraphId
    private Long nodeId;
    private String name;

    public Keyword(String name) {
        this.name = name;
    }

}
