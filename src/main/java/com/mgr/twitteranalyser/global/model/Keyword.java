package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity
@NoArgsConstructor
@Getter
public class Keyword implements Serializable {

    private Long id;
    private String name;

    public Keyword(String name) {
        this.name = name;
    }

}
