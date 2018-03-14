package com.mgr.twitteranalyser.dashboard.graph.model;

import java.io.Serializable;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.NoArgsConstructor;
import twitter4j.Status;

@NoArgsConstructor
@RelationshipEntity(type = "INTERESTED_IN")
public class Tweet implements Serializable {

    @GraphId
    private Long id;
    @EndNode
    private Keyword keyword;
    private String location;
    private String text;
    @StartNode
    private TwitterUser twitterUser;

    public Tweet(Keyword keyword, TwitterUser twitterUser, Status status) {
        this.keyword = keyword;
        this.location = status.getUser().getLocation();
        this.text = status.getText();
        this.twitterUser = twitterUser;
    }

}
