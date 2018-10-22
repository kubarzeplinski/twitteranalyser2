package com.mgr.twitteranalyser.statistics.model;

import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class StatisticsDTO {

    private final List<TwitterUser> latest5Users;
    private final Long numberOfUsers;
    private final List<String> top5Locations;
    private final List<TwitterUser> top5UsersByFollowers;

}
