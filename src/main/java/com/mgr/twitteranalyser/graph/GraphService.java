package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.keyword.KeywordService;
import com.mgr.twitteranalyser.twitteruser.TwitterUserService;
import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelation;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelation;
import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import com.mgr.twitteranalyser.graph.model.GraphDataDTO;
import com.mgr.twitteranalyser.graph.model.Link;
import com.mgr.twitteranalyser.graph.model.Node;
import com.mgr.twitteranalyser.sentiment.Sentiment;
import com.mgr.twitteranalyser.sentiment.SentimentService;
import com.mgr.twitteranalyser.utils.NumberUtils;
import com.mgr.twitteranalyser.utils.TweetUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class GraphService {

    private final KeywordService keywordService;
    private final TwitterUserService twitterUserService;
    private final SentimentService sentimentService;

    GraphDataDTO getData(String keyword) {
        keywordService.getKeywordOrThrowException(keyword);
        Set<Link> links = new HashSet<>();
        Set<Node> nodes = new HashSet<>();
        Set<TwitterUser> interestedInUsers = twitterUserService.getInterestedInUsers(keyword);
        Set<TwitterUser> retweeters = twitterUserService.getRetweeters(keyword);
        nodes.addAll(computeInterestedInNodes(interestedInUsers));
        nodes.addAll(computeRetweetersNodes(retweeters));
        nodes.add(new Node(keyword, Sentiment.NEUTRAL.getColor()));
        links.addAll(computeInterestedInLinks(interestedInUsers, keyword));
        links.addAll(computeRetweetedToLinks(retweeters));
        return new GraphDataDTO(links, nodes);
    }

    private Set<Node> computeInterestedInNodes(Set<TwitterUser> users) {
        return users
                .stream()
                .map(user -> new Node(user.getScreenName(), getInterestedInNodeColor(user.getInterestedInRelations())))
                .collect(Collectors.toSet());
    }

    private String getInterestedInNodeColor(List<InterestedInRelation> interestedInRelations) {
        List<Integer> sentiments = interestedInRelations
                .stream()
                .map(interestedInRelation -> {
                    String text = interestedInRelation.getText();
                    return sentimentService.computeSentiment(TweetUtils.cleanTweet(text));
                })
                .collect(Collectors.toList());
        return sentimentService.getSentimentColor(NumberUtils.calculateAverage(sentiments));
    }

    private Set<Node> computeRetweetersNodes(Set<TwitterUser> retweeters) {
        return retweeters.stream()
                .map(retweeter -> new Node(
                        retweeter.getScreenName(),
                        getRetweetedToNodeColor(retweeter.getRetweetedToRelations()))
                )
                .collect(Collectors.toSet());
    }

    private String getRetweetedToNodeColor(List<RetweetedToRelation> retweetedToRelations) {
        List<Integer> sentiments = retweetedToRelations
                .stream()
                .map(retweetedToRelation -> {
                    String text = retweetedToRelation.getText();
                    return sentimentService.computeSentiment(TweetUtils.cleanTweet(text));
                })
                .collect(Collectors.toList());
        return sentimentService.getSentimentColor(NumberUtils.calculateAverage(sentiments));
    }

    private Set<Link> computeInterestedInLinks(Set<TwitterUser> interestedInUsers, String keywordName) {
        return interestedInUsers
                .stream()
                .map(user -> new Link(user.getScreenName(), keywordName))
                .collect(Collectors.toSet());
    }

    private Set<Link> computeRetweetedToLinks(Set<TwitterUser> retweeters) {
        Set<Link> links = new HashSet<>();
        retweeters
                .forEach(retweeter -> {
                            List<RetweetedToRelation> relations = retweeter.getRetweetedToRelations();
                            if (relations != null) {
                                relations.forEach(retweetedToRelation ->
                                        links.add(new Link(
                                                        retweeter.getScreenName(),
                                                        retweetedToRelation.getTwitterUser().getScreenName()
                                                )
                                        )
                                );
                            }
                        }
                );
        return links;
    }

}
