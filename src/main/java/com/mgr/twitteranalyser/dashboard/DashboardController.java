package com.mgr.twitteranalyser.dashboard;

import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import com.mgr.twitteranalyser.config.apachespark.ApacheSparkConfigService;
import com.mgr.twitteranalyser.config.apachespark.TwitterCredentials;
import com.mgr.twitteranalyser.dashboard.graph.model.Keyword;
import twitter4j.Status;

@Controller
@MessageMapping(value = "/dashboard")
public class DashboardController {

    private final ApacheSparkConfigService apacheSparkConfigService;
    private final ApacheSparkService apacheSparkService;
    private JavaStreamingContext context;

    public DashboardController(ApacheSparkConfigService apacheSparkConfigService, ApacheSparkService apacheSparkService) {
        this.apacheSparkConfigService = apacheSparkConfigService;
        this.apacheSparkService = apacheSparkService;
    }

    @MessageMapping("/graphData")
    public void getGraphData(Keyword message) {
        TwitterCredentials credentials = apacheSparkConfigService.getDefaultCredentials();
        context = apacheSparkConfigService.createContext(credentials.getApplicationName());
        JavaReceiverInputDStream<Status> inputStream = apacheSparkConfigService.createStream(credentials, context);
        apacheSparkService.processData(inputStream, message.getName());
        apacheSparkConfigService.start(context);
    }

    @MessageMapping("/stop")
    public void stop() {
        apacheSparkConfigService.stop(context);
    }

}
