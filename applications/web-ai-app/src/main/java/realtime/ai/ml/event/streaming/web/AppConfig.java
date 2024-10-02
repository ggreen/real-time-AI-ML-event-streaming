package realtime.ai.ml.event.streaming.web;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.conversions.DocumentToLetterResults;
import realtime.ai.ml.event.streaming.domain.LetterResults;

@Configuration
public class AppConfig {

    @Bean
    Converter<Document, LetterResults> converter()
    {
        return new DocumentToLetterResults();
    }

}
