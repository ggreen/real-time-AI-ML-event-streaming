package com.vmware.geode.twitter.analysis;

import com.vmware.geode.twitter.domain.SentimentType;
import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.domain.TweetSentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

import java.util.Properties;

/**
 * TweetSentimentAnalysis wrapper to the <a href="https://stanfordnlp.github.io/CoreNLP/">Stanford Core NLP</a>
 *
 * @author Gregory Green
 */
public class TweetSentimentAnalysis
{

    private final StanfordCoreNLP pipeline;
    public TweetSentimentAnalysis()
    {
        final Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    public SentimentType analyzeSentiment(String tweet)
    {
        if (tweet == null || tweet.length() ==0)
            return SentimentType.NEUTRAL;

        int mainSentiment = 0;

            int longest = 0;

            Annotation annotation = pipeline.process(tweet);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {

                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);

                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);

                String partText = sentence.toString();

                if (partText.length() > longest) {

                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }

        return toCss(mainSentiment);

    }

    private SentimentType toCss(int sentimentScore) {
        switch (sentimentScore) {
            case 0:
                return SentimentType.VERY_NEGATIVE;
            case 1:
                return SentimentType.NEGATIVE;
            case 2:
                return SentimentType.NEUTRAL;
            case 3:
                return SentimentType.POSITIVE;
            case 4:
                return SentimentType.VERY_POSITIVE;
            default:
                return null;
        }
    }

    public TweetSentiment analyzeTweet(Tweet tweet)
    {
        var sentiment = this.analyzeSentiment(tweet.getText());
        var polarity = toPolarity(sentiment);
        return TweetSentiment.builder()
                    .tweet(tweet)
                    .polarity(polarity)
                    .sentimentType(sentiment)
                    .build();
    }

    protected double toPolarity(SentimentType sentiment)
    {
        switch (sentiment)
        {
            case VERY_NEGATIVE: return -2;
            case NEGATIVE: return -1;
            case NEUTRAL: return 0;
            case POSITIVE: return 1;
            case VERY_POSITIVE: return 2;
            default: throw new IllegalArgumentException(("Unknown polarity conversion of sentiment:"+sentiment));
        }
    }
}
