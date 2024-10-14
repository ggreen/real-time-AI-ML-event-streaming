package realtime.ai.ml.event.streaming.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.util.function.Function;

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
