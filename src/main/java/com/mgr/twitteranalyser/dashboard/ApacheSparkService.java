package com.mgr.twitteranalyser.dashboard;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.springframework.stereotype.Service;
import scala.Tuple2;
import twitter4j.Status;
import twitter4j.User;

@Service
public class ApacheSparkService implements Serializable {

    public void processData(JavaReceiverInputDStream<Status> inputStream, String keyWord) {
        JavaDStream<Status> filteredDStream = inputStream.filter(status ->
                StringUtils.containsIgnoreCase(status.getText(), keyWord)
        );

        JavaPairDStream<User, Status> userStatusStream =
                filteredDStream.mapToPair(
                        (status) -> new Tuple2<>(status.getUser(), status)
                );

        JavaPairDStream<User, Iterable<Status>> reducedTweets = userStatusStream.groupByKey();

        JavaPairDStream<User, Iterable<Status>> tweetsMappedByUser = reducedTweets.mapToPair(
                userTweets -> new Tuple2<User, Iterable<Status>>(userTweets._1, userTweets._2)
        );

        tweetsMappedByUser.foreachRDD((VoidFunction<JavaPairRDD<User, Iterable<Status>>>) pairRDD -> {

            pairRDD.foreach(new VoidFunction<Tuple2<User, Iterable<Status>>>() {

                @Override
                public void call(Tuple2<User, Iterable<Status>> t) {
                    System.out.println(t._1().getScreenName());
                }

            });

        });
    }

}
