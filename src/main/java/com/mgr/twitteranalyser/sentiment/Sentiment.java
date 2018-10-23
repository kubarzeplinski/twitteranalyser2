package com.mgr.twitteranalyser.sentiment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sentiment {

    VERY_NEGATIVE("very negative", "#FF0000"),
    NEGATIVE("negative", "#FFA500"),
    NEUTRAL("neutral", "#808080"),
    POSITIVE("positive", "#00FF00"),
    VERY_POSITIVE("very positive", "#008000");

    private String name;
    private String color;

}
