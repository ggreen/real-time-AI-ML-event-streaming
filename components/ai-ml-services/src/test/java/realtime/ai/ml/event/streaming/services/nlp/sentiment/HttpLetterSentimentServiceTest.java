package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterSentiment;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HttpLetterSentimentServiceTest {

    LetterSentiment expected = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
    private LetterSentimentService subject;

    @Mock
    private RestTemplate restTemplate;
    private URI uri;


    @BeforeEach
    void setUp() throws URISyntaxException {
        uri = new URI("http://loclahost:8080");
        subject = new HttpLetterSentimentService(restTemplate,uri);
    }

    @Test
    void analyze() {

        when(restTemplate.postForObject(any(URI.class),any(Letter.class),any(Class.class)))
                .thenReturn(expected);

        var actual = subject.analyze(expected.getLetter());

        assertEquals(expected, actual);
    }
}