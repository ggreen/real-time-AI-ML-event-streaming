package io.cloudNativeData.ai.web;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.cloudNativeData.ai.services.conversions.DocumentToLetterResults;
import io.cloudNativeData.ai.web.domain.LetterResults;

@Configuration
public class AppConfig {

    @Bean
    Converter<Document, LetterResults> converter()
    {
        return new DocumentToLetterResults();
    }

}
