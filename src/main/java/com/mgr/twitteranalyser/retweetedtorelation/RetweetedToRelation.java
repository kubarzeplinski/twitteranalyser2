package com.mgr.twitteranalyser.retweetedtorelation;

import com.mgr.twitteranalyser.twitteruser.TwitterUser;
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

@RelationshipEntity(type = "RETWEETED_TO")
@NoArgsConstructor
@Getter
public class RetweetedToRelation implements Serializable {

    private LocalDate createdAt;
    private GeoLocation geoLocation;
    private Long id;
    private String location;
    private Place place;
    @StartNode
    private TwitterUser retweeter;
    private String text;
    @EndNode
    private TwitterUser twitterUser;

    public RetweetedToRelation(TwitterUser retweeter, TwitterUser twitterUser, Status status) {
        this.createdAt = new java.sql.Date(status.getCreatedAt().getTime()).toLocalDate();
        this.geoLocation = status.getGeoLocation();
        this.location = status.getUser().getLocation();
        this.place = status.getPlace();
        this.text = status.getText();
        this.twitterUser = twitterUser;
        this.retweeter = retweeter;
    }

}
