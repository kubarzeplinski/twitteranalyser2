package com.mgr.twitteranalyser.statistics.service;

import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelationService;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelationService;
import com.mgr.twitteranalyser.statistics.model.StatisticsDTO;
import com.mgr.twitteranalyser.twitteruser.TwitterUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final InterestedInRelationService interestedInRelationService;
    private final RetweetedToRelationService retweetedToRelationService;
    private final TwitterUserService twitterUserService;

    public StatisticsDTO getStatistics(String keyword) {
        return StatisticsDTO.builder()
                .latest5InterestedInRelations(interestedInRelationService.getLatest5Tweets(keyword))
                .latest5RetweetedToRelations(retweetedToRelationService.getLatest5Tweets(keyword))
                .latest5Users(twitterUserService.getLatests5Users(keyword))
                .numberOfUsers(twitterUserService.countUsers(keyword))
                .top5Locations(twitterUserService.getTop5UsersLocations(keyword))
                .top5UsersByFollowers(twitterUserService.getTop5UsersByFollowers(keyword))
                .build();
    }

}
