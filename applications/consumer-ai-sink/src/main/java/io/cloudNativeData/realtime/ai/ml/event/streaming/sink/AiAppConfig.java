package io.cloudNativeData.realtime.ai.ml.event.streaming.sink;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.cloudNativeData.ai.services.conversions.LetterSentimentToDocument;
import io.cloudNativeData.ai.web.domain.nlp.LetterSentiment;

@Configuration
public class AiAppConfig {

    @Bean
    Converter<LetterSentiment, Document> converter()
    {
        return new LetterSentimentToDocument();
    }

}
