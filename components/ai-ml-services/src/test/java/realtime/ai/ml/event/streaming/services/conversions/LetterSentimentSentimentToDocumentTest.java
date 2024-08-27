package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LetterSentimentSentimentToDocumentTest {

    private LetterSentiment letterSentiment = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
    private LetterSentimentToDocument subject;


    @BeforeEach
    void setUp() {
        subject = new LetterSentimentToDocument();
    }

    @Test
    void convert() {

        SentimentType positive = SentimentType.POSITIVE;
        assertEquals(SentimentType.POSITIVE,SentimentType.valueOf(positive.name()));

        Map<String, Object> metadata = Map.of(
                "receiver", letterSentiment.getLetter().getReceiver(),
                "author", letterSentiment.getLetter().getAuthor(),
                "body", letterSentiment.getLetter().getBody(),
                "timeMs", letterSentiment.getLetter().getTimeMs(),
                 "sentiment",letterSentiment.getSentiment().name(),
                "polarity", letterSentiment.getPolarity()
                );

        var expected = new Document(letterSentiment.getLetter().getSubject(),metadata);

        var actual = subject.convert(letterSentiment);

        assertEquals(expected.getMetadata(), actual.getMetadata());
        assertEquals(expected.getContent(), actual.getContent());

    }
}