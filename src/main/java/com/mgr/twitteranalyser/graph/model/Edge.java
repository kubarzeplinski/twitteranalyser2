package com.mgr.twitteranalyser.graph.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Edge {

    private final String caption;
    private final long source;
    private final long target;

}
