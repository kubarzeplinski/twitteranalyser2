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
@Getter
public class TwitterUser implements Serializable {

    @GraphId
    private Long nodeId;

    private Date createdAt;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long id;
    private String lang;
    private String location;
    private String screenName;
    private String timeZone;

    private List<InterestedInRelation> interestedInRelations;

    public TwitterUser(User user) {
        this.createdAt = user.getCreatedAt();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFollowersCount();
        this.id = user.getId();
        this.lang = user.getLang();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();

        this.interestedInRelations = new ArrayList<>();
    }

    public void addInterestedInRelation(InterestedInRelation interestedInRelation) {
        interestedInRelations.add(interestedInRelation);
    }

}
