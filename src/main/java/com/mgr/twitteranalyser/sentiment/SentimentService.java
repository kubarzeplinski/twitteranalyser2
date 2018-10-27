package com.mgr.twitteranalyser.sentiment;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SentimentService {

    private StanfordCoreNLP pipeline;

    @PostConstruct
    public void init() {
        pipeline = new StanfordCoreNLP("stanfordnlp.properties");
    }

    public int computeSentiment(String tweet) {
        if (tweet == null || tweet.length() == 0) {
            return 0;
        }
        Annotation annotation = pipeline.process(tweet);
        List<Integer> sentiments = annotation.get(CoreAnnotations.SentencesAnnotation.class).stream()
                .map(sentence -> {
                    Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                    return RNNCoreAnnotations.getPredictedClass(tree);
                })
                .collect(Collectors.toList());
        return (int) Math.round(
                sentiments.stream()
                        .mapToDouble(sentiment -> sentiment)
                        .average()
                        .orElse(Double.NaN)
        );
    }

    public Sentiment getSentiment(int sentiment) {
        switch (sentiment) {
            case 0:
                return Sentiment.VERY_NEGATIVE;
            case 1:
                return Sentiment.NEGATIVE;
            case 2:
            default:
                return Sentiment.NEUTRAL;
            case 3:
                return Sentiment.POSITIVE;
            case 4:
                return Sentiment.VERY_POSITIVE;
        }
    }

}
