package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import twitter4j.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@NodeEntity
@Getter
public class TwitterUser implements Serializable {

    private Long id;

    @Deprecated
    private java.util.Date createdAt;
    private LocalDate createdAtLocalDate;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long userId;
    private String lang;
    private String location;
    private String screenName;
    private String timeZone;

    private List<InterestedInRelation> interestedInRelations;

    public TwitterUser(User user) {
        this.createdAt = user.getCreatedAt();
        this.createdAtLocalDate = new java.sql.Date(user.getCreatedAt().getTime()).toLocalDate();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFollowersCount();
        this.userId = user.getId();
        this.lang = user.getLang();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();

        this.interestedInRelations = new ArrayList<>();
    }

    public void addInterestedInRelation(InterestedInRelation interestedInRelation) {
        this.interestedInRelations.add(interestedInRelation);
    }

}
