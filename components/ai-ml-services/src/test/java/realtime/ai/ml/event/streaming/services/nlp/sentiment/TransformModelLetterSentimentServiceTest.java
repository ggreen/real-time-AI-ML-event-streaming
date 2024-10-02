package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransformModelLetterSentimentServiceTest {

    private TransformModelLetterSentimentService subject;
    private TransformersEmbeddingModel model;
    private LetterSentiment expected = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
    private String tokenizerUri = "";
    private String modelUri = "";

    @BeforeEach
    void setUp() {
        subject = new TransformModelLetterSentimentService(model,tokenizerUri,modelUri);
    }

    @Test
    void analyze() {
        var actual = subject.analyze(expected.getLetter());
        assertEquals(expected, actual);
    }
}