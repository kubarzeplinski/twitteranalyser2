package com.mgr.twitteranalyser.dashboard;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.springframework.stereotype.Service;
import com.mgr.twitteranalyser.graph.model.Keyword;
import com.mgr.twitteranalyser.graph.model.Tweet;
import com.mgr.twitteranalyser.graph.repository.KeywordRepository;
import com.mgr.twitteranalyser.graph.repository.UserRepository;
import scala.Tuple2;
import twitter4j.Status;

@Service
public class ApacheSparkService implements Serializable {

    private static UserRepository userRepository;
    private static KeywordRepository keywordRepository;

    public ApacheSparkService(UserRepository userRepository, KeywordRepository keywordRepository) {
        ApacheSparkService.userRepository = userRepository;
        ApacheSparkService.keywordRepository = keywordRepository;
    }

    public void processData(JavaReceiverInputDStream<Status> inputStream, String keyWordString) {
        Keyword keyword = new Keyword(keyWordString);

        JavaDStream<Status> filteredDStream = inputStream.filter(status ->
                StringUtils.containsIgnoreCase(status.getText(), keyWordString)
        );

        JavaPairDStream<twitter4j.User, Status> userStatusStream =
                filteredDStream.mapToPair(
                        (status) -> new Tuple2<>(status.getUser(), status)
                );

        userStatusStream.foreachRDD((VoidFunction<JavaPairRDD<twitter4j.User, Status>>) pairRDD -> {

            pairRDD.foreach(new VoidFunction<Tuple2<twitter4j.User, Status>>() {

                @Override
                public void call(Tuple2<twitter4j.User, Status> t) {
                    System.out.println(t._1().getScreenName());

                    Status status = t._2();
                    com.mgr.twitteranalyser.graph.model.User user = userRepository
                            .findByScreenName(status.getUser().getScreenName());
                    if (user == null) {
                        user = new com.mgr.twitteranalyser.graph.model.User(status);
                        userRepository.save(user);
                    }
                    Tweet tweet = new Tweet(keyword, user, status);
                    keyword.setTweet(tweet);
                    keywordRepository.save(keyword);
                }

            });

        });

    }

}
