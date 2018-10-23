package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.keyword.KeywordService;
import com.mgr.twitteranalyser.twitteruser.TwitterUserService;
import com.mgr.twitteranalyser.keyword.KeywordDTO;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.twitteruser.TwitterUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
public class GraphController {

    private final GraphService graphService;
    private final TwitterUserService twitterUserService;
    private final KeywordService keywordService;

    @GetMapping("/{keyword}")
    public GraphDataDTO getData(@PathVariable String keyword) {
        return graphService.getData(keyword);
    }

    @GetMapping("/keywords")
    public List<KeywordDTO> getKeywords() {
        return keywordService.getKeywords();
    }

    @GetMapping("/user/{screenName}")
    public TwitterUserDTO getUser(@PathVariable String screenName) {
        return twitterUserService.getUser(screenName);
    }

}
