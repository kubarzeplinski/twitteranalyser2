package com.mgr.twitteranalyser.statistics.service;

import com.mgr.twitteranalyser.statistics.model.StatisticsDTO;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import com.mgr.twitteranalyser.global.repository.TwitterUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatisticsService {

    private final TwitterUserRepository twitterUserRepository;

    //TODO rewrite to use TwitterUserDTO
    public StatisticsDTO getStatistics(String keyword) {
        List<TwitterUser> latest5Users = twitterUserRepository.findTop5CreatedByKeyword(keyword);
        Long numberOfUsers = twitterUserRepository.countByKeyword(keyword);
        List<String> top5Locations = twitterUserRepository.findDistinctTop5ByKeywordOrderByLocationAsc(keyword);
        List<TwitterUser> top5UsersByFollowers =
                twitterUserRepository.findTop5ByKeywordOrderByFollowersCountDesc(keyword);

        return StatisticsDTO.builder()
                .latest5Users(latest5Users)
                .numberOfUsers(numberOfUsers)
                .top5Locations(top5Locations)
                .top5UsersByFollowers(top5UsersByFollowers)
                .build();
    }

}
