package realtime.ai.ml.event.streaming.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.services.postgres.ml.sentiment.PgMlLetterSentimentService;

@Configuration
@Profile("postgres.ml.sentiment")
public class PostgresMlSentimentConfig {

    @Bean
    LetterSentimentService pgMlLetterSentimentService(JdbcTemplate jdbcTemplate)
    {
        return new PgMlLetterSentimentService(jdbcTemplate);
    }
}
