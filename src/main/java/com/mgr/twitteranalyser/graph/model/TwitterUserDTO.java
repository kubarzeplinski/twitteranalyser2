package com.mgr.twitteranalyser.graph.model;

import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@NoArgsConstructor
@Getter
public class TwitterUserDTO {

    @Deprecated
    private Date createdAt;
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

    public TwitterUserDTO(TwitterUser user) {
        this.createdAt = user.getCreatedAt();
        this.createdAtLocalDate = user.getCreatedAtLocalDate();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFriendsCount();
        this.userId = user.getUserId();
        this.lang = user.getLang();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();
    }

}
