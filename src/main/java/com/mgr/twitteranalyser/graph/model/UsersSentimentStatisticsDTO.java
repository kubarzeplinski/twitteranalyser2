package com.mgr.twitteranalyser.graph.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UsersSentimentStatisticsDTO {

    private int negativeUsers;
    private double negativeUsersPercentage;
    private int neutralUsers;
    private double neutralUsersPercentage;
    private int positiveUsers;
    private double positiveUsersPercentage;
    private int unknownUsers;
    private double unknownUsersPercentage;
    private int veryNegativeUsers;
    private double veryNegativeUsersPercentage;
    private int veryPositiveUsers;
    private double veryPositiveUsersPercentage;

}
