package com.mgr.twitteranalyser.graph.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Link {

    private final String keyword;
    private final String source;
    private final String target;
    private final RelationType type;

}
