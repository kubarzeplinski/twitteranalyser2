package com.mgr.twitteranalyser.graph.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class GraphDataDTO {

    private final Set<Edge> edges;
    private final Set<Node> nodes;

}