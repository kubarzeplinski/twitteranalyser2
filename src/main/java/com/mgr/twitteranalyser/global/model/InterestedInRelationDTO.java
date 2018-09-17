package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.Setter;
import twitter4j.GeoLocation;
import twitter4j.Place;

import java.util.Date;

@Getter
@Setter
public class InterestedInRelationDTO {

    private Date createdAt;
    private GeoLocation geoLocation;
    private String location;
    private Place place;
    private String text;

    public InterestedInRelationDTO(InterestedInRelation relation) {
        this.createdAt = relation.getCreatedAt();
        this.geoLocation = relation.getGeoLocation();
        this.location = relation.getLocation();
        this.place = relation.getPlace();
        this.text = relation.getText();
    }

}
