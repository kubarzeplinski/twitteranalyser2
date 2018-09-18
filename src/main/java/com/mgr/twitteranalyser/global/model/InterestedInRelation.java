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
import java.time.LocalDate;

@RelationshipEntity(type = "INTERESTED_IN")
@NoArgsConstructor
@Getter
public class InterestedInRelation implements Serializable {

    @Deprecated
    private java.util.Date createdAt;
    private LocalDate createdAtLocalDate;
    private GeoLocation geoLocation;
    private Long id;
    @EndNode
    private Keyword keyword;
    private String location;
    private Place place;
    private String text;
    @StartNode
    private TwitterUser twitterUser;

    public InterestedInRelation(Keyword keyword, TwitterUser twitterUser, Status status) {
        this.createdAt = status.getCreatedAt();
        this.createdAtLocalDate = new java.sql.Date(status.getCreatedAt().getTime()).toLocalDate();
        this.geoLocation = status.getGeoLocation();
        this.keyword = keyword;
        this.location = status.getUser().getLocation();
        this.place = status.getPlace();
        this.text = status.getText();
        this.twitterUser = twitterUser;
    }

}
