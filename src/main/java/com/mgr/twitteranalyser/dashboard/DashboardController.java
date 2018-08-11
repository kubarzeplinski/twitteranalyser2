package com.mgr.twitteranalyser.dashboard;

import com.mgr.twitteranalyser.config.apachespark.ApacheSparkConfigService;
import com.mgr.twitteranalyser.config.apachespark.TwitterCredentials;
import com.mgr.twitteranalyser.dashboard.model.Keyword;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import twitter4j.Status;

import java.util.Optional;

@RestController
@EnableScheduling
@RequestMapping(value = "/dashboard")
@Slf4j
public class DashboardController {

    private final ApacheSparkConfigService apacheSparkConfigService;
    private final ApacheSparkService apacheSparkService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StatsService statsService;

    private JavaStreamingContext context;
    private volatile boolean contextInitialized = false;
    private volatile boolean apacheSparkStarted = false;
    private volatile String keywordName;

    public DashboardController(ApacheSparkConfigService apacheSparkConfigService,
                               ApacheSparkService apacheSparkService,
                               SimpMessagingTemplate simpMessagingTemplate,
                               StatsService statsService) {
        this.apacheSparkConfigService = apacheSparkConfigService;
        this.apacheSparkService = apacheSparkService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.statsService = statsService;
    }

    @PostMapping("/start")
    public void start(@RequestBody Keyword keyword) {
        keywordName = keyword.getName();
        TwitterCredentials credentials = apacheSparkConfigService.getDefaultCredentials();
        context = apacheSparkConfigService.createContext(credentials.getApplicationName());
        JavaReceiverInputDStream<Status> inputStream = apacheSparkConfigService.createStream(credentials, context);
        contextInitialized = true;
        apacheSparkStarted = true;
        log.info("Apache Spark started.");
        apacheSparkService.processData(inputStream, keyword.getName());
        apacheSparkConfigService.start(context);
    }

    @PutMapping("/stop")
    public void stop() {
        if (contextInitialized && apacheSparkStarted) {
            Optional.ofNullable(apacheSparkConfigService)
                    .ifPresent(service -> {
                        apacheSparkStarted = false;
                        service.stop(context);
                        log.info("Apache Spark stopped.");
                    });
        }
    }

    @Scheduled(fixedRate = 10000)
    public void getGraphData() {
        if (contextInitialized && apacheSparkStarted) {
            log.info("Sending graph data...");
            simpMessagingTemplate.convertAndSend("/dashboard/graphData", statsService.getStats(keywordName));
        }
    }

}
