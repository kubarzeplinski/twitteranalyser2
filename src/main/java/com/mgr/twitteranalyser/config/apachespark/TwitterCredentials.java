package com.mgr.twitteranalyser.config.apachespark;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class TwitterCredentials {

    private String applicationName;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;

    public TwitterCredentials(String applicationName, String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.applicationName = applicationName;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;
        validateProperties(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    private void validateProperties(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        Assert.notNull(consumerKey, "consumerKey cannot be null");
        Assert.notNull(consumerSecret, "consumerSecret cannot be null");
        Assert.notNull(accessToken, "accessToken cannot be null");
        Assert.notNull(accessTokenSecret, "accessTokenSecret cannot be null");
    }

}
