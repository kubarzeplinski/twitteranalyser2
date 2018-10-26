package com.mgr.twitteranalyser.retweetedtorelation;

import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import lombok.Getter;
import lombok.Setter;
import twitter4j.GeoLocation;
import twitter4j.Place;

import java.time.LocalDateTime;

@Getter
@Setter
public class RetweetedToRelationDTO {

    private LocalDateTime createdAt;
    private GeoLocation geoLocation;
    private String language;
    private String location;
    private Place place;
    private TwitterUser retweeter;
    private String text;
    private TwitterUser twitterUser;

    public RetweetedToRelationDTO(RetweetedToRelation relation) {
        this.createdAt = relation.getCreatedAt();
        this.geoLocation = relation.getGeoLocation();
        this.language = relation.getLanguage();
        this.location = relation.getLocation();
        this.place = relation.getPlace();
        this.retweeter = relation.getRetweeter();
        this.text = relation.getText();
        this.twitterUser = relation.getTwitterUser();
    }

}
