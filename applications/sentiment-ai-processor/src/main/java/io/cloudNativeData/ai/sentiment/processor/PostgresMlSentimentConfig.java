package io.cloudNativeData.ai.sentiment.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import io.cloudNativeData.ai.services.nlp.sentiment.LetterSentimentService;
import io.cloudNativeData.ai.services.postgres.ml.sentiment.PgMlLetterSentimentService;

@Configuration
@Profile("postgres.ml.sentiment")
public class PostgresMlSentimentConfig {

    @Bean
    LetterSentimentService pgMlLetterSentimentService(JdbcTemplate jdbcTemplate)
    {
        return new PgMlLetterSentimentService(jdbcTemplate);
    }
}
