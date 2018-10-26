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
import java.time.LocalDateTime;

@RelationshipEntity(type = "RETWEETED_TO")
@NoArgsConstructor
@Getter
public class RetweetedToRelation implements Serializable {

    private LocalDateTime createdAt;
    private GeoLocation geoLocation;
    private Long id;
    private String language;
    private String location;
    private Place place;
    @StartNode
    private TwitterUser retweeter;
    private String text;
    @EndNode
    private TwitterUser twitterUser;

    public RetweetedToRelation(TwitterUser retweeter, TwitterUser twitterUser, Status status) {
        this.createdAt = new java.sql.Timestamp(status.getCreatedAt().getTime()).toLocalDateTime();
        this.geoLocation = status.getGeoLocation();
        this.language = status.getLang();
        this.location = status.getUser().getLocation();
        this.place = status.getPlace();
        this.text = status.getText();
        this.twitterUser = twitterUser;
        this.retweeter = retweeter;
    }

}
