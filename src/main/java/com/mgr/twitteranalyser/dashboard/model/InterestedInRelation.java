package com.mgr.twitteranalyser.dashboard.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import twitter4j.Status;

import java.io.Serializable;

@NoArgsConstructor
@RelationshipEntity(type = "INTERESTED_IN")
public class InterestedInRelation implements Serializable {

    private Long id;
    @EndNode
    @Getter
    private Keyword keyword;
    private String location;
    private String text;
    @StartNode
    @Getter
    private TwitterUser twitterUser;

    public InterestedInRelation(Keyword keyword, TwitterUser twitterUser, Status status) {
        this.keyword = keyword;
        this.location = status.getUser().getLocation();
        this.text = status.getText();
        this.twitterUser = twitterUser;
    }

}
