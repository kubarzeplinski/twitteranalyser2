package com.mgr.twitteranalyser.dashboard;

import com.mgr.twitteranalyser.dashboard.model.StatsDTO;
import com.mgr.twitteranalyser.dashboard.model.TwitterUser;
import com.mgr.twitteranalyser.dashboard.repository.TwitterUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StatsService {

    private final TwitterUserRepository twitterUserRepository;

    StatsDTO getStats(String keyword) {
        List<TwitterUser> latest5Users = twitterUserRepository.findTop5ByLastKeywordOrderByCreatedAtDesc(keyword);
        Long numberOfUsers = twitterUserRepository.countByLastKeyword(keyword);
        List<String> top5Locations = twitterUserRepository.findDistinctTop5ByLastKeywordOrderByLocationAsc(keyword);
        List<TwitterUser> top5UsersByFollowers =
                twitterUserRepository.findTop5ByLastKeywordOrderByFollowersCountDesc(keyword);

        return StatsDTO.builder()
                .latest5Users(latest5Users)
                .numberOfUsers(numberOfUsers)
                .top5Locations(top5Locations)
                .top5UsersByFollowers(top5UsersByFollowers)
                .build();
    }

}
