package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentToLetterSentimentResultsTest {
    private LetterResults expected = JavaBeanGeneratorCreator.of(LetterResults.class).create();

    @Mock
    private Document document;
    private DocumentToLetterResults subject;


    @BeforeEach
    void setUp() {
        subject = new DocumentToLetterResults();
    }

    @Test
    void convert() {
        Map<String, Object> metaData  = Map.of(
                "body",expected.getLetter().getBody(),
                "distance",expected.getDistance(),
                "receiver",expected.getLetter().getReceiver(),
                "author",expected.getLetter().getAuthor(),
                "timeMs",expected.getLetter().getTimeMs(),
                "polarity", expected.getPolarity(),
                "sentiment", expected.getSentiment().toString()
                );

        when(document.getText()).thenReturn(expected.getLetter().getSubject());
        when(document.getMetadata()).thenReturn(metaData);

        var actual = subject.convert(document);

        assertEquals(expected, actual);
    }

    @Test
    void convert_sentimentNull_convertsTo_Neutal() {
        Map<String, Object> metaData  = Map.of(
                "body",expected.getLetter().getBody(),
                "distance",expected.getDistance(),
                "receiver",expected.getLetter().getReceiver(),
                "author",expected.getLetter().getAuthor(),
                "timeMs",expected.getLetter().getTimeMs(),
                "polarity", expected.getPolarity()
        );

        when(document.getText()).thenReturn(expected.getLetter().getSubject());
        when(document.getMetadata()).thenReturn(metaData);

        var actual = subject.convert(document);

        assertEquals(SentimentType.NEUTRAL, actual.getSentiment());
    }
    @Test
    void convert_metaDataNull() {

        when(document.getText()).thenReturn(expected.getLetter().getSubject());

        assertDoesNotThrow( () ->  subject.convert(document));

    }
}