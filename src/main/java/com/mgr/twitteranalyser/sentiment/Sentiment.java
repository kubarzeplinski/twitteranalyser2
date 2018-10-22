package com.mgr.twitteranalyser.sentiment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Sentiment {

    VERY_NEGATIVE("very negative", "#FF0000"),
    NEGATIVE("negative", "#FFD400"),
    NEUTRAL("neitral", "#FFFF00"),
    POSITIVE("positive", "#D4FF00"),
    VERY_POSITIVE("very positive", "#00FF00");

    private String name;
    private String color;

}
