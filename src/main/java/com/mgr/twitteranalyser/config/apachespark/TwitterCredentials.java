package com.mgr.twitteranalyser.config.apachespark;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class TwitterCredentials {

    private final String applicationName;
    private final String consumerKey;
    private final String consumerSecret;
    private final String accessToken;
    private final String accessTokenSecret;

    public TwitterCredentials(String applicationName, String consumerKey, String consumerSecret, String accessToken,
                              String accessTokenSecret) {
        this.applicationName = applicationName;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        validateProperties(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    private void validateProperties(String consumerKey, String consumerSecret, String accessToken,
                                    String accessTokenSecret) {
        Assert.notNull(consumerKey, "consumerKey cannot be null");
        Assert.notNull(consumerSecret, "consumerSecret cannot be null");
        Assert.notNull(accessToken, "accessToken cannot be null");
        Assert.notNull(accessTokenSecret, "accessTokenSecret cannot be null");
    }

}
