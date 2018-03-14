package com.mgr.twitteranalyser.dashboard.graph.model;

import java.io.Serializable;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import lombok.NoArgsConstructor;
import twitter4j.GeoLocation;
import twitter4j.Place;
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
    private TwitterUser twitterUser;

    public Tweet(Keyword keyword, TwitterUser twitterUser, Status status) {
        this.keyword = keyword;
        this.twitterUser = twitterUser;
        this.text = status.getText();
        setLocation(status);
    }

    private void setLocation(Status status) {
        GeoLocation location = null;
        Place place = status.getPlace();

        if (status.getGeoLocation() != null) {
            location = status.getGeoLocation();
        } else if (place != null && place.getGeometryCoordinates() != null) {
            location = status.getPlace().getGeometryCoordinates()[0][0];
        }

        if (location == null) {
            return;
        }

        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

}
