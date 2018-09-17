package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.global.model.KeywordDTO;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
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

    @GetMapping("/{keyword}")
    public GraphDataDTO getData(@PathVariable String keyword) {
        return graphService.getData(keyword);
    }

    @GetMapping("/keywords")
    public List<KeywordDTO> getKeywords() {
        return graphService.getKeywords();
    }

}
