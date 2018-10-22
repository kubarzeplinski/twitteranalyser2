package com.mgr.twitteranalyser.twitteruser;

import com.mgr.twitteranalyser.graph.model.TwitterUserDTO;
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
                .findAllInterestedInByKeyword(keyword)
                .collect(Collectors.toSet());
    }

    public Set<TwitterUser> getRetweeters(String keyword) {
        return twitterUserRepository
                .findAllRetweetedToByKeyword(keyword)
                .collect(Collectors.toSet());
    }

}
