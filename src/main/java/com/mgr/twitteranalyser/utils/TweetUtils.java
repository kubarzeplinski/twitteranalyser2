package com.mgr.twitteranalyser.utils;

public final class TweetUtils {

    private TweetUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated.");
    }

    public static String cleanTweet(String tweetData) {
        tweetData = tweetData.toLowerCase();
        tweetData = tweetData.replaceAll("(@[A-Za-z0-9_]+)|([^0-9A-Za-z \\t])|(\\w+:\\/\\/\\S+)", " ");
        return tweetData;
    }

}
