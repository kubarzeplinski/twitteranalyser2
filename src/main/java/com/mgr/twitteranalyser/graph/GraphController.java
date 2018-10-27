package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.graph.model.LinkDataDTO;
import com.mgr.twitteranalyser.graph.model.RelationDataDTO;
import com.mgr.twitteranalyser.keyword.KeywordDTO;
import com.mgr.twitteranalyser.keyword.KeywordService;
import com.mgr.twitteranalyser.twitteruser.TwitterUserDTO;
import com.mgr.twitteranalyser.twitteruser.TwitterUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graphs")
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

    @GetMapping("/users/{screenName}")
    public TwitterUserDTO getUser(@PathVariable String screenName) {
        return twitterUserService.getUser(screenName);
    }

    @PostMapping("/relation")
    public List<RelationDataDTO> getRelationData(@RequestBody LinkDataDTO dto) {
        return graphService.getRelationData(dto);
    }

}
