package realtime.ai.ml.event.streaming.services.postgres.ml.sentiment;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostgresMlLetterSentimentServiceTest {

    private PgMlLetterSentimentService subject;

    private Letter letterSentiment = JavaBeanGeneratorCreator.of(Letter.class).create();
    private SentimentType sentiment = SentimentType.NEGATIVE;
    private double polarity = 1.0;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        subject = new PgMlLetterSentimentService(jdbcTemplate);
    }

    @Test
    void analyze() {
        var expected = realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment
                .builder()
                .letter(letterSentiment)
                .sentiment(sentiment)
                .polarity(polarity)
                .build();


        List<realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment> list = asList(expected);

        when(jdbcTemplate.query(anyString(),
                any(RowMapper.class), any(Object[].class)))
                .thenReturn(list);

        var actual = subject.analyze(letterSentiment);

        assertEquals(expected, actual);
    }

    @Test
    void toSentiment_Neutral_when_null() {
        assertEquals(SentimentType.NEUTRAL,subject.toSentiment(null));
    }

    @Test
    void toSentiment_Neutral_when_empty() {
        assertEquals(SentimentType.NEUTRAL,subject.toSentiment(""));
    }

    @Test
    void toSentiment_POSITIVE() {
        assertEquals(SentimentType.POSITIVE,subject.toSentiment("POSITIVE"));
    }

    @Test
    void toSentiment_NEGATIVE() {
        assertEquals(SentimentType.NEGATIVE,subject.toSentiment("NEGATIVE"));
    }
}