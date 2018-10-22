package com.mgr.twitteranalyser.statistics;

import com.mgr.twitteranalyser.config.apachespark.ApacheSparkConfigService;
import com.mgr.twitteranalyser.config.apachespark.TwitterCredentials;
import com.mgr.twitteranalyser.keyword.KeywordDTO;
import com.mgr.twitteranalyser.statistics.service.ApacheSparkService;
import com.mgr.twitteranalyser.statistics.service.StatisticsService;
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
@RequestMapping(value = "/statistics")
@Slf4j
public class StatisticsController {

    private final ApacheSparkConfigService apacheSparkConfigService;
    private final ApacheSparkService apacheSparkService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StatisticsService statisticsService;

    private JavaStreamingContext context;
    private volatile boolean contextInitialized = false;
    private volatile boolean apacheSparkStarted = false;
    private volatile String keywordName;

    public StatisticsController(ApacheSparkConfigService apacheSparkConfigService,
                                ApacheSparkService apacheSparkService,
                                SimpMessagingTemplate simpMessagingTemplate,
                                StatisticsService statisticsService) {
        this.apacheSparkConfigService = apacheSparkConfigService;
        this.apacheSparkService = apacheSparkService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.statisticsService = statisticsService;
    }

    @PostMapping("/start")
    public void start(@RequestBody KeywordDTO keyword) {
        keywordName = keyword.getName();
        TwitterCredentials credentials = apacheSparkConfigService.getDefaultCredentials();
        context = apacheSparkConfigService.createContext(credentials.getApplicationName());
        JavaReceiverInputDStream<Status> inputStream = apacheSparkConfigService.createStream(credentials, context);
        contextInitialized = true;
        apacheSparkStarted = true;
        log.info("Apache Spark started.");
        apacheSparkService.processData(inputStream, keywordName);
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
    public void getStatisticsData() {
        if (contextInitialized && apacheSparkStarted) {
            log.info("Sending graph data...");
            simpMessagingTemplate.convertAndSend("/statistics/statisticsData", statisticsService.getStatistics(keywordName));
        }
    }

}
