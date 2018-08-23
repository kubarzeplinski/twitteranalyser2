package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@RelationshipEntity(type = "INTERESTED_IN")
public class InterestedInRelation implements Serializable {

    private Date createdAt;
    private GeoLocation geoLocation;
    private Long id;
    @EndNode
    @Getter
    private Keyword keyword;
    private String location;
    private Place place;
    private String text;
    @StartNode
    @Getter
    private TwitterUser twitterUser;

    public InterestedInRelation(Keyword keyword, TwitterUser twitterUser, Status status) {
        this.createdAt = status.getCreatedAt();
        this.geoLocation = status.getGeoLocation();
        this.keyword = keyword;
        this.location = status.getUser().getLocation();
        this.place = status.getPlace();
        this.text = status.getText();
        this.twitterUser = twitterUser;
    }

}
