package com.mgr.twitteranalyser.retweetedtorelation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RetweetedToRelationService {

    private final RetweetedToRelationRepository retweetedToRelationRepository;

    public Set<String> getLatest5Tweets(String keyword) {
        return retweetedToRelationRepository.findLatest5Tweets(keyword);
    }

    public List<String> getRelations(String keyword, String userName, String retweeterName) {
        return retweetedToRelationRepository.findRelations(keyword, userName, retweeterName);
    }

}
