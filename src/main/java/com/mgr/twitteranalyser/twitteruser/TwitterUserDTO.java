package com.mgr.twitteranalyser.twitteruser;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class TwitterUserDTO {

    private LocalDate createdAt;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long userId;
    private String language;
    private String location;
    private String screenName;
    private String timeZone;

    public TwitterUserDTO(TwitterUser user) {
        this.createdAt = user.getCreatedAt();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFriendsCount();
        this.userId = user.getUserId();
        this.language = user.getLanguage();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();
    }

}
