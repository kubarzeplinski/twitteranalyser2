package com.mgr.twitteranalyser.global.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import twitter4j.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@NodeEntity
@Getter
public class TwitterUser implements Serializable {

    private Long id;

    private Date createdAt;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long userId;
    private String lang;
    private String lastKeyword;
    private String location;
    private String screenName;
    private String timeZone;

    private List<InterestedInRelation> interestedInRelations;

    public TwitterUser(User user, String keyword) {
        this.createdAt = user.getCreatedAt();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFollowersCount();
        this.userId = user.getId();
        this.lang = user.getLang();
        this.lastKeyword = keyword;
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();

        this.interestedInRelations = new ArrayList<>();
    }

    public void addInterestedInRelation(InterestedInRelation interestedInRelation) {
        this.interestedInRelations.add(interestedInRelation);
    }

}
