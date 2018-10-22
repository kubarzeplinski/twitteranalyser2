package com.mgr.twitteranalyser.keyword;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    public List<KeywordDTO> getKeywords() {
        return keywordRepository.readAllByNameNotNull()
                .map(KeywordDTO::new)
                .sorted(Comparator.comparing(KeywordDTO::getName))
                .collect(Collectors.toList());
    }

    public Keyword getKeywordOrThrowException(String keyword) {
        return keywordRepository.findByName(keyword)
                .orElseThrow(() -> new RuntimeException(String.format("Keyword with name %s not found.", keyword)));
    }

}
