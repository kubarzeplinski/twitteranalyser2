package com.mgr.twitteranalyser.utils;

import java.util.List;

public class NumberUtils {

    private NumberUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static int calculateAverage(List<Integer> list) {
        return (int) Math.round(
                list.stream()
                        .mapToDouble(el -> el)
                        .average()
                        .orElse(Double.NaN)
        );
    }

    public static double calculatePercentage(int value, int sum) {
        return Math.round((double) value / sum * 100.0);
    }

}
