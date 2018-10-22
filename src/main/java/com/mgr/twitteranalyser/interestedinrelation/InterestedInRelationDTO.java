package com.mgr.twitteranalyser.interestedinrelation;

import lombok.Getter;
import lombok.Setter;
import twitter4j.GeoLocation;
import twitter4j.Place;

import java.time.LocalDate;

@Getter
@Setter
public class InterestedInRelationDTO {

    @Deprecated
    private java.util.Date createdAt;
    private LocalDate createdAtLocalDate;
    private GeoLocation geoLocation;
    private String location;
    private Place place;
    private String text;

    public InterestedInRelationDTO(InterestedInRelation relation) {
        this.createdAt = relation.getCreatedAt();
        this.createdAtLocalDate = relation.getCreatedAtLocalDate();
        this.geoLocation = relation.getGeoLocation();
        this.location = relation.getLocation();
        this.place = relation.getPlace();
        this.text = relation.getText();
    }

}
