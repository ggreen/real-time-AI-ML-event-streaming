package com.vmware.geode.twitter.analysis;

import com.vmware.geode.twitter.domain.SentimentType;
import com.vmware.geode.twitter.domain.Tweet;
import com.vmware.geode.twitter.domain.TweetSentiment;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TweetSentimentAnalysisTest
{

    private TweetSentimentAnalysis subject = new TweetSentimentAnalysis();
    private Tweet tweet = JavaBeanGeneratorCreator.of(Tweet.class).create();

    @Test
    void analyzeSentiment()
    {
        var subject = new TweetSentimentAnalysis();
        tweet.setText("Imani is greatness little sister ever");
        var actual = subject.analyzeSentiment(tweet.getText());
        assertEquals(SentimentType.POSITIVE,actual);
    }

    @Test
    void analyzeTweet()
    {
        String id = "0";
        var tweet = new Tweet(id,"Imani and Nyla are the best daughters ever. Always trying their best");
        var actual = subject.analyzeTweet(tweet);
        double polarity = 1;
        var expected = TweetSentiment.builder()
                                     .tweet(tweet)
                                     .sentimentType(SentimentType.POSITIVE)
                .polarity(1).build();

        assertEquals(expected,actual);
    }

    @Test
    void analyzeSentiment_WhenNegative()
    {
        var subject = new TweetSentimentAnalysis();
        tweet.setText("Joe has been just talking and talking, can he just be quiet, stop it already, be quiet");
        var actual = subject.analyzeSentiment(tweet.getText());
        assertEquals(SentimentType.NEGATIVE,actual);
    }

    @Nested
    class PolarityTest
    {
        @Test
        void toPolarity_When_NEURTAL_THAN_0()
        {
            assertEquals(0,subject.toPolarity(SentimentType.NEUTRAL));
        }
        @Test
        void toPolarity_When_NEGATIVE_THAN_Minus1()
        {
            assertEquals(-1,subject.toPolarity(SentimentType.NEGATIVE));
        }

        @Test
        void toPolarity_When_POSTIVE_THAN_1()
        {
            assertEquals(1,subject.toPolarity(SentimentType.POSITIVE));
        }
        @Test
        void toPolarity_When_VERy_NEGATIVE_THAN_Minus2()
        {
            assertEquals(-2,subject.toPolarity(SentimentType.VERY_NEGATIVE));
        }
        @Test
        void toPolarity_When_VERY_POSITIVE_THAN_2()
        {
            assertEquals(2,subject.toPolarity(SentimentType.VERY_POSITIVE));
        }
    }

}