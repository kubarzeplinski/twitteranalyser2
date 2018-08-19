package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/graph")
@AllArgsConstructor
public class GraphController {

    private final GraphService graphService;

    @GetMapping("/{keyword}")
    public GraphDataDTO getData(@PathVariable String keyword) {
        return graphService.getData(keyword);
    }

}
