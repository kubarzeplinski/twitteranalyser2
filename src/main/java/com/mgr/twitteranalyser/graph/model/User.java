package com.mgr.twitteranalyser.graph.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import twitter4j.Status;

import java.util.Date;

@NoArgsConstructor
@NodeEntity
public class User {

    @GraphId
    private Long nodeId;
    @Getter
    private String screenName;
    private String location;
    private String description;
    private int followersCount;
    private int friendsCount;
    private int favouritesCount;
    private Date createdAt;
    private String timeZone;
    private String lang;

    public User(Status status) {
        twitter4j.User user = status.getUser();
        this.screenName = user.getScreenName();
        this.location = user.getLocation();
        this.description = user.getDescription();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFollowersCount();
        this.favouritesCount = user.getFavouritesCount();
        this.createdAt = user.getCreatedAt();
        this.timeZone = user.getTimeZone();
        this.lang = user.getLang();
    }

    public User(String screenName) {
        this.screenName = screenName;
    }

}
