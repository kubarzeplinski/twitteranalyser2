package com.mgr.twitteranalyser.dashboard.graph.model;

import java.io.Serializable;
import java.util.Date;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import twitter4j.User;

@NoArgsConstructor
@NodeEntity
public class TwitterUser implements Serializable {

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

    public TwitterUser(User user) {
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

}
