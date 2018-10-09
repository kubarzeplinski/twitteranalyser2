package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.model.KeywordDTO;
import com.mgr.twitteranalyser.global.model.RetweetedToRelation;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import com.mgr.twitteranalyser.global.repository.KeywordRepository;
import com.mgr.twitteranalyser.global.repository.TwitterUserRepository;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.graph.model.Link;
import com.mgr.twitteranalyser.graph.model.TwitterUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class GraphService {

    private final KeywordRepository keywordRepository;
    private final TwitterUserRepository twitterUserRepository;

    GraphDataDTO getData(String keyword) {
        if (!keywordExists(keyword)) {
            return null;
        }
        Set<Link> links = new HashSet<>();
        links.addAll(computeInterestedInLinks(getInterestedInUsers(keyword), keyword));
        links.addAll(computeRetweetedToLinks(getRetweetedToUsers(keyword)));
        return new GraphDataDTO(links);
    }

    private boolean keywordExists(String keyword) {
        Keyword word = keywordRepository.findByName(keyword);
        return word != null;
    }

    private Set<TwitterUser> getInterestedInUsers(String keyword) {
        return twitterUserRepository
                .findAllInterestedInByKeyword(keyword)
                .collect(Collectors.toSet());
    }

    private Set<Link> computeInterestedInLinks(Set<TwitterUser> interestedInUsers, String keywordName) {
        return interestedInUsers
                .stream()
                .map(user -> new Link(user.getScreenName(), keywordName))
                .collect(Collectors.toSet());
    }

    private Set<TwitterUser> getRetweetedToUsers(String keyword) {
        return twitterUserRepository
                .findAllRetweetedToByKeyword(keyword)
                .collect(Collectors.toSet());
    }

    private Set<Link> computeRetweetedToLinks(Set<TwitterUser> retweetedToUsers) {
        Set<Link> links = new HashSet<>();
        retweetedToUsers
                .forEach(user -> {
                            List<RetweetedToRelation> relations = user.getRetweetedToRelations();
                            if (relations != null) {
                                relations.forEach(retweetedToRelation ->
                                        links.add(new Link(
                                                        user.getScreenName(),
                                                        retweetedToRelation.getTwitterUser().getScreenName()
                                                )
                                        )
                                );
                            }
                        }
                );
        return links;
    }

    public List<KeywordDTO> getKeywords() {
        return keywordRepository.readAllByNameNotNull()
                .map(KeywordDTO::new)
                .sorted(Comparator.comparing(KeywordDTO::getName))
                .collect(Collectors.toList());
    }

    public TwitterUserDTO getUser(String screenName) {
        TwitterUser twitterUser = twitterUserRepository.findByScreenName(screenName)
                .orElseThrow(
                        () -> new RuntimeException(String.format("User with screen name: %s not found.", screenName))
                );
        return new TwitterUserDTO(twitterUser);
    }

}
