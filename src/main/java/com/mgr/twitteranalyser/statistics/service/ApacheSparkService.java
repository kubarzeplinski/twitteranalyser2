package com.mgr.twitteranalyser.statistics.service;

import com.mgr.twitteranalyser.global.model.InterestedInRelation;
import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.model.RetweetedToRelation;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import com.mgr.twitteranalyser.global.repository.KeywordRepository;
import com.mgr.twitteranalyser.global.repository.TwitterUserRepository;
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

    public void processData(JavaReceiverInputDStream<Status> inputStream, String keywordString) {
        String finalKeywordString = keywordString.toLowerCase();

        Keyword keyword = keywordRepository.findByName(finalKeywordString);
        if (keyword == null) {
            keyword = new Keyword(finalKeywordString);
            keywordRepository.save(keyword);
        }

        JavaDStream<Status> filteredDStream = inputStream.filter(status ->
                StringUtils.containsIgnoreCase(status.getText(), finalKeywordString)
        );

        JavaPairDStream<User, Status> userStatusStream =
                filteredDStream.mapToPair((status) -> new Tuple2<>(status.getUser(), status));

        Keyword finalKeyword1 = keyword;
        Keyword finalKeyword2 = keyword;
        userStatusStream.foreachRDD((VoidFunction<JavaPairRDD<User, Status>>) pairRDD ->
                pairRDD.foreach((VoidFunction<Tuple2<User, Status>>) t -> {
                    User user = t._1();
                    Status status = t._2();

                    TwitterUser twitterUser = twitterUserRepository.findByUserId(user.getId());
                    if (twitterUser == null) {
                        twitterUser = new TwitterUser(user);
                    }

                    if (status.getRetweetedStatus() != null) {
                        prepareRetweetedToRelation(finalKeyword1, twitterUser, status);
                    } else {
                        InterestedInRelation interestedInRelation = new InterestedInRelation(
                                finalKeyword2,
                                twitterUser,
                                status
                        );
                        finalKeyword1.addInterestedInRelation(interestedInRelation);
                        keywordRepository.save(finalKeyword1);
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
            prepareRetweetedToRelation(keyword, twitterUser, retweetedStatus);
        }
        InterestedInRelation interestedInRelation = new InterestedInRelation(keyword, twitterUser, status);
        keyword.addInterestedInRelation(interestedInRelation);
        keywordRepository.save(keyword);
    }

}