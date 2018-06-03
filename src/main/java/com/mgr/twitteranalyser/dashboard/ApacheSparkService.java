package com.mgr.twitteranalyser.dashboard;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.springframework.stereotype.Service;
import com.mgr.twitteranalyser.dashboard.graph.model.Keyword;
import com.mgr.twitteranalyser.dashboard.graph.model.InterestedInRelation;
import com.mgr.twitteranalyser.dashboard.graph.model.TwitterUser;
import com.mgr.twitteranalyser.dashboard.graph.repository.KeywordRepository;
import com.mgr.twitteranalyser.dashboard.graph.repository.TwitterUserRepository;
import scala.Tuple2;
import twitter4j.Status;
import twitter4j.User;

@Service
public class ApacheSparkService implements Serializable {

    private static TwitterUserRepository twitterUserRepository;
    private static KeywordRepository keywordRepository;

    public ApacheSparkService(TwitterUserRepository twitterUserRepository, KeywordRepository keywordRepository) {
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

        Keyword finalKeyword = keyword;
        userStatusStream.foreachRDD((VoidFunction<JavaPairRDD<User, Status>>) pairRDD -> {

            pairRDD.foreach(new VoidFunction<Tuple2<User, Status>>() {

                @Override
                public void call(Tuple2<User, Status> t) {
                    User user = t._1();
                    Status status = t._2();

                    TwitterUser twitterUser = twitterUserRepository.findById(user.getId());
                    if (twitterUser == null) {
                        twitterUser = new TwitterUser(user);
                    }
                    InterestedInRelation interestedInRelation = new InterestedInRelation(finalKeyword, twitterUser, status);
                    twitterUser.addTweet(interestedInRelation);
                    twitterUserRepository.save(twitterUser);
                }

            });

        });

    }

}
