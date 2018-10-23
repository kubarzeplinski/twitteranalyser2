package com.mgr.twitteranalyser.statistics.model;

import com.mgr.twitteranalyser.twitteruser.TwitterUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@AllArgsConstructor
@Getter
public class StatisticsDTO {

    private final Set<String> latest5InterestedInRelations;
    private final Set<String> latest5RetweetedToRelations;
    private final Set<TwitterUserDTO> latest5Users;
    private final Long numberOfUsers;
    private final Set<String> top5Locations;
    private final Set<TwitterUserDTO> top5UsersByFollowers;

}
