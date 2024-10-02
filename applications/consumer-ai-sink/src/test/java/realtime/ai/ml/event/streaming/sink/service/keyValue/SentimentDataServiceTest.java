package realtime.ai.ml.event.streaming.sink.service.keyValue;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.sink.repository.LetterSentimentRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SentimentDataServiceTest {

    @Mock
    private LetterSentimentRepository repository;
    private LetterSentiment letterSentiment;

    private SentimentDataService subject;

    @BeforeEach
    void setUp() {
        letterSentiment = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
        subject = new SentimentDataService(repository);
    }

    @Test
    void save() {
        subject.save(letterSentiment);

        verify(repository).save(any(LetterSentiment.class));
    }
}