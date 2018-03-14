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
import com.mgr.twitteranalyser.dashboard.graph.model.Tweet;
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
        Keyword keyword = new Keyword(keywordString);

        JavaDStream<Status> filteredDStream = inputStream.filter(status ->
                StringUtils.containsIgnoreCase(status.getText(), keywordString)
        );

        JavaPairDStream<User, Status> userStatusStream =
                filteredDStream.mapToPair((status) -> new Tuple2<>(status.getUser(), status));

        userStatusStream.foreachRDD((VoidFunction<JavaPairRDD<User, Status>>) pairRDD -> {

            pairRDD.foreach(new VoidFunction<Tuple2<User, Status>>() {

                @Override
                public void call(Tuple2<User, Status> t) {
                    User user = t._1();
                    Status status = t._2();

                    TwitterUser twitterUser = twitterUserRepository.findByScreenName(user.getScreenName());
                    if (twitterUser == null) {
                        twitterUser = new TwitterUser(user);
                        twitterUserRepository.save(twitterUser);
                    }
                    Tweet tweet = new Tweet(keyword, twitterUser, status);
                    keyword.setTweet(tweet);
                    keywordRepository.save(keyword);
                }

            });

        });

    }

}
