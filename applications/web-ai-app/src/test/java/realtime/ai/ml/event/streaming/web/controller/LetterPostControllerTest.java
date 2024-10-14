package realtime.ai.ml.event.streaming.web.controller;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import nyla.solutions.core.patterns.integration.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LetterPostControllerTest {

    private LetterPostController subject;
    @Mock
    private Publisher<Letter> publisher;
    private Letter letter = JavaBeanGeneratorCreator.of(Letter.class).create();

    @BeforeEach
    void setUp() {
        subject = new LetterPostController(publisher);
    }

    @Test
    void postLetter() {
        subject.postLetter(letter);
        verify(publisher).send(any());
    }
}