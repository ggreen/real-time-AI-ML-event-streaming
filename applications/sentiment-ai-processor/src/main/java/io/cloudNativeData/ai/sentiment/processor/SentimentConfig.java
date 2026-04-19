package io.cloudNativeData.ai.sentiment.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.cloudNativeData.ai.services.nlp.sentiment.LetterSentimentService;
import io.cloudNativeData.ai.web.domain.Letter;
import io.cloudNativeData.ai.web.domain.nlp.LetterSentiment;

import java.util.function.Function;

/**
 * ML sentiment configurations
 * @author gregory green
 */
@Configuration
@Slf4j
public class SentimentConfig {

    @Bean
    Function<Letter, LetterSentiment> letterToSentiment(LetterSentimentService service) {
        return  letter -> {
            log.info("letter: {}",letter);
            var letterSentiment = service.analyze(letter);
            log.info("letterSentiment: {}",letterSentiment);
            return letterSentiment;
        };
    }
}
