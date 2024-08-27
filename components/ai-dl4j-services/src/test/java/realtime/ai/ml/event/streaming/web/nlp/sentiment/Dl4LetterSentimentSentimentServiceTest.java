package realtime.ai.ml.event.streaming.web.nlp.sentiment;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Dl4LetterSentimentSentimentServiceTest {

    private Dl4LetterSentimentService subject;

    private LetterSentiment expected = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();

    @Mock
    private MultiLayerNetwork model;
    @Mock
    private WordVectors wordVectors;

    @BeforeEach
    void setUp() {
        subject = new Dl4LetterSentimentService(model, wordVectors);
    }

    @Test
    void analyze() {
        expected.setSentiment(SentimentType.NEGATIVE);
        when(wordVectors.hasWord(anyString())).thenReturn(true);

        assertEquals(expected, subject.analyze(expected.getLetter()));
    }


    @Test
    void toFeature() {
        var actual = subject.toFeature(expected.getLetter());

        assertThat(actual).isNotNull();
    }
}