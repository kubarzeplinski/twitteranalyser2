package com.mgr.twitteranalyser.twitteruser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TwitterUserService {

    private final TwitterUserRepository twitterUserRepository;

    public TwitterUserDTO getUser(String screenName) {
        TwitterUser twitterUser = twitterUserRepository.findByScreenName(screenName)
                .orElseThrow(
                        () -> new RuntimeException(String.format("User with screen name %s not found.", screenName))
                );
        return new TwitterUserDTO(twitterUser);
    }

    public Set<TwitterUser> getInterestedInUsers(String keyword) {
        return twitterUserRepository
                .findAllInterestedIn(keyword)
                .collect(Collectors.toSet());
    }

    public Set<TwitterUser> getRetweeters(String keyword) {
        return twitterUserRepository
                .findAllRetweetedTo(keyword)
                .collect(Collectors.toSet());
    }

    public Set<TwitterUserDTO> getLatests5Users(String keyword) {
        return twitterUserRepository.findLatest5(keyword)
                .map(TwitterUserDTO::new)
                .collect(Collectors.toSet());

    }

    public Long countUsers(String keyword) {
        return twitterUserRepository.getCount(keyword);
    }

    public Set<String> getTop5UsersLocations(String keyword) {
        return twitterUserRepository.findTop5Locations(keyword);
    }

    public Set<TwitterUserDTO> getTop5UsersByFollowers(String keyword) {
        return twitterUserRepository.findTop5ByFollowers(keyword)
                .map(TwitterUserDTO::new)
                .collect(Collectors.toSet());

    }

}
