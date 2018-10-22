package com.mgr.twitteranalyser.statistics.service;

import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelation;
import com.mgr.twitteranalyser.keyword.Keyword;
import com.mgr.twitteranalyser.keyword.KeywordRepository;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelation;
import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import com.mgr.twitteranalyser.twitteruser.TwitterUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import scala.Tuple2;
import twitter4j.Status;
import twitter4j.User;

import java.io.Serializable;

@Service
@Transactional
public class ApacheSparkService implements Serializable {

    private static TwitterUserRepository twitterUserRepository;
    private static KeywordRepository keywordRepository;

    public ApacheSparkService(TwitterUserRepository twitterUserRepository,
                              KeywordRepository keywordRepository) {
        ApacheSparkService.twitterUserRepository = twitterUserRepository;
        ApacheSparkService.keywordRepository = keywordRepository;
    }

    public void processData(JavaReceiverInputDStream<Status> inputStream, String keywordName) {
        String finalKeywordName = keywordName.toLowerCase();
        Keyword keyword = keywordRepository.findByName(finalKeywordName)
                .orElseGet(() -> {
                    Keyword k = new Keyword(finalKeywordName);
                    keywordRepository.save(k);
                    return k;
                });
        JavaDStream<Status> filteredDStream = inputStream.filter(status ->
                StringUtils.containsIgnoreCase(status.getText(), finalKeywordName)
        );
        JavaPairDStream<User, Status> userStatusStream = filteredDStream.mapToPair(
                (status) -> new Tuple2<>(status.getUser(), status)
        );
        userStatusStream.foreachRDD((VoidFunction<JavaPairRDD<User, Status>>) pairRDD ->
                pairRDD.foreach((VoidFunction<Tuple2<User, Status>>) t -> {
                    User user = t._1();
                    Status status = t._2();
                    TwitterUser twitterUser = twitterUserRepository.findByUserId(user.getId());
                    if (twitterUser == null) {
                        twitterUser = new TwitterUser(user);
                    }

                    if (status.getRetweetedStatus() != null) {
                        prepareRetweetedToRelation(keyword, twitterUser, status);
                    } else {
                        InterestedInRelation interestedInRelation = new InterestedInRelation(
                                keyword,
                                twitterUser,
                                status
                        );
                        twitterUser.addInterestedInRelation(interestedInRelation);
                        twitterUserRepository.save(twitterUser);
                    }
                })
        );
    }

    private void prepareRetweetedToRelation(Keyword keyword, TwitterUser retweeter, Status status) {
        Status retweetedStatus = status.getRetweetedStatus();
        TwitterUser twitterUser = twitterUserRepository.findByUserId(retweetedStatus.getUser().getId());
        if (twitterUser == null) {
            twitterUser = new TwitterUser(retweetedStatus.getUser());
        }
        RetweetedToRelation retweetedToRelation = new RetweetedToRelation(retweeter, twitterUser, status);
        retweeter.addRetweetedToRelation(retweetedToRelation);
        twitterUserRepository.save(retweeter);
        if (retweetedStatus.getRetweetedStatus() != null) {
            prepareRetweetedToRelation(keyword, retweeter, retweetedStatus);
        }
        InterestedInRelation interestedInRelation = new InterestedInRelation(keyword, twitterUser, status);
        twitterUser.addInterestedInRelation(interestedInRelation);
        twitterUserRepository.save(twitterUser);
    }

}