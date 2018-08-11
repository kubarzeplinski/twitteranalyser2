package com.mgr.twitteranalyser.dashboard.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class StatsDTO {

    private final List<TwitterUser> latest5Users;
    private final Long numberOfUsers;
    private final List<String> top5Locations;
    private final List<TwitterUser> top5UsersByFollowers;

}
