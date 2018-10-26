package com.mgr.twitteranalyser.graph.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsersSentimentStatisticsDTO {

    private int negativeUsers;
    private int neutralUsers;
    private int positiveUsers;
    private int veryNegativeUsers;
    private int veryPositiveUsers;

}
