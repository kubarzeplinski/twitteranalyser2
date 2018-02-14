package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.graph.model.Keyword;
import com.mgr.twitteranalyser.graph.model.Tweet;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Controller
public class GraphController {


    @MessageMapping("/graph/data")
    @SendTo("/graph/data")
    public Tweet getData(Keyword message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Tweet("Hello, " + message.getName() + "!");
    }

}
