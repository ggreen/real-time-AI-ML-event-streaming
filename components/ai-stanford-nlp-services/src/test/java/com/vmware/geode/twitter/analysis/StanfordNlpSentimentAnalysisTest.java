package com.vmware.geode.twitter.analysis;


import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import realtime.ai.ml.event.streaming.stanford.sentiment.StanfordNlpSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import static org.junit.jupiter.api.Assertions.*;

class StanfordNlpSentimentAnalysisTest
{

    private StanfordNlpSentimentService subject = new StanfordNlpSentimentService();
    private Letter letter = JavaBeanGeneratorCreator.of(Letter.class).create();

    @Test
    void analyzeSentiment()
    {
        var subject = new StanfordNlpSentimentService();
        letter.setSubject("Imani is greatness little sister ever");
        var actual = subject.analyzeSentiment(letter.getSubject());
        assertEquals(SentimentType.POSITIVE,actual);
    }

    @Test
    void analyzeTweet()
    {
        String id = "0";
        var letter = Letter.builder().subject("Imani and Nyla are the best daughters ever. Always trying their best")
                .build();
        var actual = subject.analyze(letter);
        double polarity = 1;
        var expected = LetterSentiment.builder()
                                     .letter(letter)
                                     .sentiment(SentimentType.POSITIVE)
                .polarity(1).build();

        assertEquals(expected,actual);
    }

    @Test
    void analyzeSentiment_WhenNegative()
    {
        var subject = new StanfordNlpSentimentService();
        letter.setSubject("Joe has been just talking and talking, can he just be quiet, stop it already, be quiet");
        var actual = subject.analyzeSentiment(letter.getSubject());
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