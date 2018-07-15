package com.mgr.twitteranalyser.dashboard;

import java.util.Optional;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mgr.twitteranalyser.config.apachespark.ApacheSparkConfigService;
import com.mgr.twitteranalyser.config.apachespark.TwitterCredentials;
import com.mgr.twitteranalyser.dashboard.graph.model.Keyword;
import com.mgr.twitteranalyser.dashboard.graph.repository.TwitterUserRepository;
import lombok.extern.slf4j.Slf4j;
import twitter4j.Status;

@RestController
@EnableScheduling
@RequestMapping(value = "/dashboard")
@Slf4j
public class DashboardController {

    private final ApacheSparkConfigService apacheSparkConfigService;
    private final ApacheSparkService apacheSparkService;
    private final TwitterUserRepository twitterUserRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private JavaStreamingContext context;
    private volatile boolean contextInitialized = false;
    private volatile boolean apacheSparkStarted = false;

    public DashboardController(ApacheSparkConfigService apacheSparkConfigService,
                               ApacheSparkService apacheSparkService,
                               TwitterUserRepository twitterUserRepository,
                               SimpMessagingTemplate simpMessagingTemplate) {
        this.apacheSparkConfigService = apacheSparkConfigService;
        this.apacheSparkService = apacheSparkService;
        this.twitterUserRepository = twitterUserRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/start")
    public void start(@RequestBody Keyword keyword) {
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
            //TODO prepare information to send to frontend
            simpMessagingTemplate.convertAndSend("/dashboard/graphData", "Hello");
        }
    }

}
