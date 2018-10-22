package com.mgr.twitteranalyser.twitteruser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelation;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import twitter4j.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NodeEntity
@NoArgsConstructor
@Getter
public class TwitterUser implements Serializable {

    @Deprecated
    private java.util.Date createdAt;
    private LocalDate createdAtLocalDate;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    @Id
    @GeneratedValue
    private Long id;
    private long userId;
    private String lang;
    private String location;
    private String screenName;
    private String timeZone;

    @Relationship(type = "RETWEETED_TO")
    private List<RetweetedToRelation> retweetedToRelations = new ArrayList<>();

    @JsonIgnoreProperties("twitterUser")
    @Relationship(type = "INTERESTED_IN")
    private List<InterestedInRelation> interestedInRelations = new ArrayList<>();

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
    }

    public void addRetweetedToRelation(RetweetedToRelation relation) {
        this.retweetedToRelations.add(relation);
    }

    public void addInterestedInRelation(InterestedInRelation interestedInRelation) {
        this.interestedInRelations.add(interestedInRelation);
    }

}
