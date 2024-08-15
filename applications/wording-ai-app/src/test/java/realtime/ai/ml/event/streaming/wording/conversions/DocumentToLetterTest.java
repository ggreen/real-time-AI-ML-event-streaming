package realtime.ai.ml.event.streaming.wording.conversions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.wording.domain.LetterResults;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentToLetterTest {
    private LetterResults expected = JavaBeanGeneratorCreator.of(LetterResults.class).create();

    @Mock
    private Document document;
    private DocumentToLetter subject;


    @BeforeEach
    void setUp() {
        subject = new DocumentToLetter();
    }

    @Test
    void convert() {
        Map<String, Object> metaData  = Map.of(
                "body",expected.getLetter().getBody(),
                "distance",expected.getDistance(),
                "to",expected.getLetter().getTo(),
                "from",expected.getLetter().getFrom());

        when(document.getContent()).thenReturn(expected.getLetter().getSubject());
        when(document.getMetadata()).thenReturn(metaData);

        var actual = subject.convert(document);

        assertEquals(expected, actual);
    }

    @Test
    void convert_metaDataNull() {


        when(document.getContent()).thenReturn(expected.getLetter().getSubject());

        assertDoesNotThrow( () ->  subject.convert(document));

    }
}