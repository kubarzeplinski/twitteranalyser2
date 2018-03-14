package com.mgr.twitteranalyser.dashboard.graph.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private Date createdAt;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private String lang;
    private String location;
    @Getter
    private String screenName;
    private String timeZone;

    private List<Tweet> interestedIn;

    public TwitterUser(User user) {
        this.createdAt = user.getCreatedAt();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFollowersCount();
        this.lang = user.getLang();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();

        this.interestedIn = new ArrayList<>();
    }

    public void addTweet(Tweet tweet) {
        interestedIn.add(tweet);
    }

}
