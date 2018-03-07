package com.mgr.twitteranalyser.graph;

import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.mgr.twitteranalyser.config.apachespark.ApacheSparkConfigService;
import com.mgr.twitteranalyser.config.apachespark.TwitterCredentials;
import com.mgr.twitteranalyser.dashboard.ApacheSparkService;
import com.mgr.twitteranalyser.graph.model.Keyword;
import twitter4j.Status;

@Controller
@MessageMapping(value = "/graph")
public class GraphController {

    private ApacheSparkConfigService apacheSparkConfigService;
    private ApacheSparkService apacheSparkService;

    public GraphController(ApacheSparkConfigService apacheSparkConfigService, ApacheSparkService apacheSparkService) {
        this.apacheSparkConfigService = apacheSparkConfigService;
        this.apacheSparkService = apacheSparkService;
    }

    @MessageMapping("/data")
    public void getData(Keyword message) {
        TwitterCredentials credentials = apacheSparkConfigService.getDefaultCredentials();
        JavaStreamingContext context = apacheSparkConfigService.createContext(credentials.getApplicationName());
        JavaReceiverInputDStream<Status> inputStream = apacheSparkConfigService.createStream(credentials, context);
        apacheSparkService.processData(inputStream, message.getName());
        apacheSparkConfigService.start(context);
    }

}
