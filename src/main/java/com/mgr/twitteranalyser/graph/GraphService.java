package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.global.model.Keyword;
import com.mgr.twitteranalyser.global.model.KeywordDTO;
import com.mgr.twitteranalyser.global.model.TwitterUser;
import com.mgr.twitteranalyser.global.repository.KeywordRepository;
import com.mgr.twitteranalyser.global.repository.TwitterUserRepository;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.graph.model.Link;
import com.mgr.twitteranalyser.graph.model.Node;
import com.mgr.twitteranalyser.graph.model.TwitterUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
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

        Set<Link> links = nodes
                .stream()
                .map(node -> new Link(node.getCaption(), keywordNode.getCaption()))
                .collect(Collectors.toSet());

        return new GraphDataDTO(links);
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

    public List<KeywordDTO> getKeywords() {
        return keywordRepository.readAllByNameNotNull()
                .map(KeywordDTO::new)
                .sorted(Comparator.comparing(KeywordDTO::getName))
                .collect(Collectors.toList());
    }

    public TwitterUserDTO getUser(String screenName) {
        TwitterUser twitterUser = twitterUserRepository.findByScreenName(screenName)
                .orElseThrow(
                        () -> new RuntimeException(String.format("User with screen name: %s not found.", screenName))
                );
        return new TwitterUserDTO(twitterUser);
    }

}
