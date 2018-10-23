package com.mgr.twitteranalyser.interestedinrelation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class InterestedInRelationService {

    private final InterestedInRelationRepository interestedInRelationRepository;

    public Set<String> getLatest5Tweets(String keyword) {
        return interestedInRelationRepository.findLatest5(keyword);
    }

}
