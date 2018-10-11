package com.mgr.twitteranalyser.global.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
@NoArgsConstructor
@Getter
public class Keyword implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @JsonIgnoreProperties("keyword")
    @Relationship(type = "INTERESTED_IN", direction = Relationship.INCOMING)
    private List<InterestedInRelation> interestedInRelations = new ArrayList<>();

    public Keyword(String name) {
        this.name = name;
    }

    public void addInterestedInRelation(InterestedInRelation interestedInRelation) {
        this.interestedInRelations.add(interestedInRelation);
    }

}
