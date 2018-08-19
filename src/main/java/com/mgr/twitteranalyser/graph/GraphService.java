package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.repository.KeywordRepository;
import com.mgr.twitteranalyser.global.repository.TwitterUserRepository;
import com.mgr.twitteranalyser.graph.model.Edge;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.graph.model.Node;
import com.mgr.twitteranalyser.graph.model.RelationType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GraphService {

    private final KeywordRepository keywordRepository;
    private final TwitterUserRepository twitterUserRepository;

    GraphDataDTO getData(String keyword) {
        Node keywordNode = getKeywordNode(keyword);
        if (keywordNode == null) {
            return null;
        }
        Set<Node> nodes = getUsers(keyword);

        Set<Edge> edges = nodes
                .stream()
                .map(node -> new Edge(RelationType.INTERESTED_IN.name(), node.getId(), keywordNode.getId()))
                .collect(Collectors.toSet());

        nodes.add(keywordNode);

        return new GraphDataDTO(edges, nodes);
    }

    private Node getKeywordNode(String keyword) {
        Keyword word = keywordRepository.findByName(keyword);
        if (word != null) {
            return new Node(word);
        }
        return null;
    }

    private Set<Node> getUsers(String keyword) {
        return twitterUserRepository
                .findAllByKeyword(keyword)
                .map(Node::new)
                .collect(Collectors.toSet());
    }

}
