package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LetterToDocumentTest {

    private Letter letter = JavaBeanGeneratorCreator.of(Letter.class).create();
    private LetterToDocument subject;


    @BeforeEach
    void setUp() {
        subject = new LetterToDocument();
    }

    @Test
    void convert() {

        Map<String, Object> metadata = Map.of(
                "receiver",letter.getReceiver(),
                "author",letter.getAuthor(),
                "body",letter.getBody(),
                "timeMs",letter.getTimeMs()
                );

        var expected = new Document(letter.getSubject(),metadata);

        var actual = subject.convert(letter);

        assertEquals(expected.getMetadata(), actual.getMetadata());
        assertEquals(expected.getContent(), actual.getContent());

    }
}