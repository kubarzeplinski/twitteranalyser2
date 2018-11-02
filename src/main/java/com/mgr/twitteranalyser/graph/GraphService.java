package com.mgr.twitteranalyser.graph;

import com.mgr.twitteranalyser.graph.model.*;
import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelation;
import com.mgr.twitteranalyser.interestedinrelation.InterestedInRelationService;
import com.mgr.twitteranalyser.keyword.KeywordService;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelation;
import com.mgr.twitteranalyser.retweetedtorelation.RetweetedToRelationService;
import com.mgr.twitteranalyser.sentiment.Sentiment;
import com.mgr.twitteranalyser.sentiment.SentimentService;
import com.mgr.twitteranalyser.twitteruser.TwitterUser;
import com.mgr.twitteranalyser.twitteruser.TwitterUserService;
import com.mgr.twitteranalyser.utils.NumberUtils;
import com.mgr.twitteranalyser.utils.TweetUtils;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.mgr.twitteranalyser.utils.NumberUtils.calculatePercentage;

@Service
@Transactional
@AllArgsConstructor
public class GraphService {

    private static final String ENGLISH_LANGUAGE_ABBREVIATION = "en";

    private final KeywordService keywordService;
    private final TwitterUserService twitterUserService;
    private final SentimentService sentimentService;
    private final InterestedInRelationService interestedInRelationService;
    private final RetweetedToRelationService retweetedToRelationService;

    GraphDataDTO getData(String keyword) {
        keywordService.getKeywordOrThrowException(keyword);
        Set<Link> links = new HashSet<>();
        Set<Node> nodes = new HashSet<>();
        Set<TwitterUser> interestedInUsers = twitterUserService.getInterestedInUsers(keyword);
        Set<TwitterUser> retweeters = twitterUserService.getRetweeters(keyword);
        nodes.addAll(computeInterestedInNodes(interestedInUsers, keyword));
        nodes.addAll(computeRetweetersNodes(retweeters, keyword));
        UsersSentimentStatisticsDTO sentimentStatistics = computeUsersSentimentStatistics(nodes);
        nodes.add(new Node(keyword, Sentiment.NEUTRAL.getColor(), 1000));
        links.addAll(computeInterestedInLinks(interestedInUsers, keyword));
        links.addAll(computeRetweetedToLinks(retweeters, keyword));
        return new GraphDataDTO(links, nodes, sentimentStatistics);
    }

    private Set<Node> computeInterestedInNodes(Set<TwitterUser> users, String keyword) {
        return users
                .stream()
                .map(user -> new Node(
                                user.getScreenName(),
                                getInterestedInNodeColor(user.getInterestedInRelations(), keyword),
                                user.getFollowersCount()
                        )
                )
                .collect(Collectors.toSet());
    }

    private String getInterestedInNodeColor(List<InterestedInRelation> interestedInRelations, String keyword) {
        if (!allEnglishInterestedInRelations(interestedInRelations, keyword)) {
            return Sentiment.UNKNOWN.getColor();
        }
        List<Integer> sentiments = interestedInRelations
                .stream()
                .filter(interestedInRelation ->
                        StringUtils.containsIgnoreCase(interestedInRelation.getText(), keyword) &&
                                ENGLISH_LANGUAGE_ABBREVIATION.equals(interestedInRelation.getLanguage())
                )
                .map(interestedInRelation -> {
                    String text = interestedInRelation.getText();
                    return sentimentService.computeSentiment(TweetUtils.cleanTweet(text));
                })
                .collect(Collectors.toList());
        if (sentiments.isEmpty()) {
            return Sentiment.NEUTRAL.getColor();
        }
        return sentimentService.getSentiment(NumberUtils.calculateAverage(sentiments)).getColor();
    }

    private boolean allEnglishInterestedInRelations(List<InterestedInRelation> interestedInRelations, String keyword) {
        return interestedInRelations
                .stream()
                .filter(interestedInRelation -> StringUtils.containsIgnoreCase(interestedInRelation.getText(), keyword))
                .allMatch(interestedInRelation -> ENGLISH_LANGUAGE_ABBREVIATION.equals(interestedInRelation.getLanguage()));
    }

    private Set<Node> computeRetweetersNodes(Set<TwitterUser> retweeters, String keyword) {
        return retweeters.stream()
                .map(retweeter -> new Node(
                                retweeter.getScreenName(),
                                getRetweetedToNodeColor(retweeter.getRetweetedToRelations(), keyword),
                                retweeter.getFollowersCount()
                        )
                )
                .collect(Collectors.toSet());
    }

    private String getRetweetedToNodeColor(List<RetweetedToRelation> retweetedToRelations, String keyword) {
        if (!allEnglishRetweetedToRelations(retweetedToRelations, keyword)) {
            return Sentiment.UNKNOWN.getColor();
        }
        List<Integer> sentiments = retweetedToRelations
                .stream()
                .filter(retweetedToRelation -> StringUtils.containsIgnoreCase(retweetedToRelation.getText(), keyword))
                .map(retweetedToRelation -> {
                    String text = retweetedToRelation.getText();
                    return sentimentService.computeSentiment(TweetUtils.cleanTweet(text));
                })
                .collect(Collectors.toList());
        if (sentiments.isEmpty()) {
            return Sentiment.NEUTRAL.getColor();
        }
        return sentimentService.getSentiment(NumberUtils.calculateAverage(sentiments)).getColor();
    }

    private boolean allEnglishRetweetedToRelations(List<RetweetedToRelation> retweetedToRelations, String keyword) {
        return retweetedToRelations
                .stream()
                .filter(retweetedToRelation -> StringUtils.containsIgnoreCase(retweetedToRelation.getText(), keyword))
                .allMatch(retweetedToRelation -> ENGLISH_LANGUAGE_ABBREVIATION.equals(retweetedToRelation.getLanguage()));
    }

    private UsersSentimentStatisticsDTO computeUsersSentimentStatistics(Set<Node> nodes) {
        Map<String, List<Node>> map = nodes.stream()
                .collect(Collectors.groupingBy(Node::getColor));
        List<Node> negativeNodes = map.get(Sentiment.NEGATIVE.getColor());
        List<Node> neutralNodes = map.get(Sentiment.NEUTRAL.getColor());
        List<Node> positiveNodes = map.get(Sentiment.POSITIVE.getColor());
        List<Node> veryNegativeNodes = map.get(Sentiment.VERY_NEGATIVE.getColor());
        List<Node> veryPositiveNodes = map.get(Sentiment.VERY_POSITIVE.getColor());
        List<Node> unknownNodes = map.get(Sentiment.UNKNOWN.getColor());
        int nodesSize = nodes.size();
        return UsersSentimentStatisticsDTO.builder()
                .negativeUsers(negativeNodes != null ? negativeNodes.size() : 0)
                .negativeUsersPercentage(negativeNodes != null ? calculatePercentage(negativeNodes.size(), nodesSize) : 0)
                .neutralUsers(neutralNodes != null ? neutralNodes.size() : 0)
                .neutralUsersPercentage(neutralNodes != null ? calculatePercentage(neutralNodes.size(), nodesSize) : 0)
                .positiveUsers(positiveNodes != null ? positiveNodes.size() : 0)
                .positiveUsersPercentage(positiveNodes != null ? calculatePercentage(positiveNodes.size(), nodesSize) : 0)
                .unknownUsers(unknownNodes != null ? unknownNodes.size() : 0)
                .unknownUsersPercentage(unknownNodes != null ? calculatePercentage(unknownNodes.size(), nodesSize) : 0)
                .veryNegativeUsers(veryNegativeNodes != null ? veryNegativeNodes.size() : 0)
                .veryNegativeUsersPercentage(veryNegativeNodes != null ? calculatePercentage(veryNegativeNodes.size(), nodesSize) : 0)
                .veryPositiveUsers(veryPositiveNodes != null ? veryPositiveNodes.size() : 0)
                .veryPositiveUsersPercentage(veryPositiveNodes != null ? calculatePercentage(veryPositiveNodes.size(), nodesSize) : 0)
                .build();
    }

    private Set<Link> computeInterestedInLinks(Set<TwitterUser> interestedInUsers, String keywordName) {
        return interestedInUsers
                .stream()
                .map(user -> new Link(
                        keywordName,
                        user.getScreenName(),
                        keywordName,
                        RelationType.INTERESTED_IN)
                )
                .collect(Collectors.toSet());
    }

    private Set<Link> computeRetweetedToLinks(Set<TwitterUser> retweeters, String keyword) {
        Set<Link> links = new HashSet<>();
        retweeters
                .forEach(retweeter -> {
                            List<RetweetedToRelation> relations = retweeter.getRetweetedToRelations();
                            if (relations != null) {
                                relations.forEach(retweetedToRelation ->
                                        links.add(new Link(
                                                        keyword,
                                                        retweeter.getScreenName(),
                                                        retweetedToRelation.getTwitterUser().getScreenName(),
                                                        RelationType.RETWEETED_TO
                                                )
                                        )
                                );
                            }
                        }
                );
        return links;
    }

    public List<RelationDataDTO> getRelationData(LinkDataDTO dto) {
        List<String> results = Collections.emptyList();
        if (dto.getType() == RelationType.INTERESTED_IN) {
            results = interestedInRelationService.getRelations(dto.getKeyword(), dto.getSource());
        } else if (dto.getType() == RelationType.RETWEETED_TO) {
            results = retweetedToRelationService.getRelations(dto.getKeyword(), dto.getTarget(), dto.getSource());
        }
        return results.stream()
                .map(result -> prepareRelationData(dto, result))
                .collect(Collectors.toList());
    }

    private RelationDataDTO prepareRelationData(LinkDataDTO dto, String result) {
        String[] results = result.split("!#!#!RELATION!#!#!");
        String text = results[2];
        Sentiment sentiment = ENGLISH_LANGUAGE_ABBREVIATION.equals(results[1]) ?
                sentimentService.getSentiment(sentimentService.computeSentiment(TweetUtils.cleanTweet(text))) :
                Sentiment.UNKNOWN;
        return RelationDataDTO.builder()
                .createdAt(results[0])
                .keyword(dto.getKeyword())
                .language(results[1])
                .sentiment(sentiment)
                .source(dto.getSource())
                .target(dto.getTarget())
                .text(text)
                .type(dto.getType())
                .build();
    }

}
