package com.mgr.twitteranalyser.config.apachespark;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class ApacheSparkConfigService {

    @Value("${spring.social.twitter.application-name")
    private String applicationName;
    @Value("${spring.social.twitter.consumer-key}")
    private String consumerKey;
    @Value("${spring.social.twitter.consumer-secret}")
    private String consumerSecret;
    @Value("${spring.social.twitter.access-token}")
    private String accessToken;
    @Value("${spring.social.twitter.access-token-secret}")
    private String accessTokenSecret;

    public TwitterCredentials getDefaultCredentials() {
        return new TwitterCredentials(applicationName, consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    public JavaStreamingContext createContext(String applicationName) {
        SparkConf sparkConf = new SparkConf().setAppName(applicationName).setMaster("local[10]");
        return new JavaStreamingContext(sparkConf, new Duration(10000));
    }

    public JavaReceiverInputDStream<Status> createStream(TwitterCredentials credentials, JavaStreamingContext context) {
        Authorization twitterAuth = new OAuthAuthorization(createConfiguration(credentials));
        return TwitterUtils.createStream(context, twitterAuth, new String[]{});
    }

    private Configuration createConfiguration(TwitterCredentials credentials) {
        return new ConfigurationBuilder()
                .setDebugEnabled(false)
                .setOAuthConsumerKey(credentials.getConsumerKey())
                .setOAuthConsumerSecret(credentials.getConsumerSecret())
                .setOAuthAccessToken(credentials.getAccessToken())
                .setOAuthAccessTokenSecret(credentials.getAccessTokenSecret())
                .build();
    }

    public void start(JavaStreamingContext context) {
        context.start();
        context.awaitTermination();
    }

    public void stop(JavaStreamingContext context) {
        context.stop();
    }

}
