package realtime.ai.ml.event.streaming.processor.functions;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterSentiment;
import realtime.ai.ml.event.streaming.web.domain.Sentiment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterSentimentFunctionTest {


    private LetterSentimentFunction subject;
    private Letter letter = JavaBeanGeneratorCreator.of(Letter.class).create();

    @Mock
    private LetterSentimentService service;


    @BeforeEach
    void setUp() {
        subject = new LetterSentimentFunction(service);
    }

    @Test
    void apply() {
        LetterSentiment expected = LetterSentiment.builder()
                .letter(letter)
                .sentiment(Sentiment
                        .positive).build();

        when(service.analyze(any(Letter.class))).thenReturn(expected);
        var actual = subject.apply(letter);

        assertEquals(expected, actual);

    }
}