package com.mgr.twitteranalyser.graph.model;

import java.io.Serializable;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.NoArgsConstructor;
import twitter4j.GeoLocation;
import twitter4j.Status;

@NoArgsConstructor
@RelationshipEntity(type = "INTERESTED_IN")
public class Tweet implements Serializable {

    Long id;
    private String text;
    private double latitude;
    private double longitude;
    @EndNode
    private Keyword keyword;
    @StartNode
    private User user;

    public Tweet(Keyword keyword, User user, Status status) {
        this.keyword = keyword;
        this.user = user;
        this.text = status.getText();
        setLocation(status);
    }

    public Tweet(Keyword keyword, User user, String text) {
        this.keyword = keyword;
        this.user = user;
        this.text = text;
    }

    private void setLocation(Status status) {
        GeoLocation location = null;

        if (status.getGeoLocation() != null) {
            location = status.getGeoLocation();
        } else if (status.getPlace() != null) {
            location = status.getPlace().getGeometryCoordinates()[0][0];
        }
        if (location == null) {
            return;
        }
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

}
