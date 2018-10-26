package com.mgr.twitteranalyser.interestedinrelation;

import lombok.Getter;
import lombok.Setter;
import twitter4j.GeoLocation;
import twitter4j.Place;

import java.time.LocalDateTime;

@Getter
@Setter
public class InterestedInRelationDTO {

    private LocalDateTime createdAt;
    private GeoLocation geoLocation;
    private String language;
    private String location;
    private Place place;
    private String text;
    private int sentiment;

    public InterestedInRelationDTO(InterestedInRelation relation) {
        this.createdAt = relation.getCreatedAt();
        this.geoLocation = relation.getGeoLocation();
        this.language = relation.getLanguage();
        this.location = relation.getLocation();
        this.place = relation.getPlace();
        this.text = relation.getText();
    }

}
