package com.mgr.twitteranalyser.graph.model;

import com.mgr.twitteranalyser.global.model.InterestedInRelation;
import com.mgr.twitteranalyser.global.model.InterestedInRelationDTO;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class TwitterUserDTO {

    private Date createdAt;
    private String description;
    private int favouritesCount;
    private int followersCount;
    private int friendsCount;
    private long userId;
    private String lang;
    private String location;
    private String screenName;
    private String timeZone;

    private List<InterestedInRelationDTO> interestedInRelations;

    public TwitterUserDTO(TwitterUser user) {
        this.createdAt = user.getCreatedAt();
        this.description = user.getDescription();
        this.favouritesCount = user.getFavouritesCount();
        this.followersCount = user.getFollowersCount();
        this.friendsCount = user.getFriendsCount();
        this.userId = user.getUserId();
        this.lang = user.getLang();
        this.location = user.getLocation();
        this.screenName = user.getScreenName();
        this.timeZone = user.getTimeZone();
        this.interestedInRelations = prepareRelations(user.getInterestedInRelations());
    }

    private List<InterestedInRelationDTO> prepareRelations(List<InterestedInRelation> relations) {
        if (relations == null) {
            return null;
        }
        return relations
                .stream()
                .map(InterestedInRelationDTO::new)
                .collect(Collectors.toList());
    }

}
