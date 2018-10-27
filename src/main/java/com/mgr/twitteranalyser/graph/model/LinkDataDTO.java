package com.mgr.twitteranalyser.graph.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkDataDTO {

    private String keyword;
    private String source;
    private String target;
    private RelationType type;

}
