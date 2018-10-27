package com.mgr.twitteranalyser.graph.model;

import com.mgr.twitteranalyser.sentiment.Sentiment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class RelationDataDTO {

    private String createdAt;
    private String keyword;
    private String language;
    private Sentiment sentiment;
    private String source;
    private String target;
    private String text;
    private RelationType type;

}
