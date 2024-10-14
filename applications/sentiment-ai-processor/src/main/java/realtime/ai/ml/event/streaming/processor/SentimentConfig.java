package realtime.ai.ml.event.streaming.processor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.util.function.Function;

@Configuration
public class SentimentConfig {




    @Bean
    Function<Letter, LetterSentiment> letterToSentiment(LetterSentimentService service) {
        return letter -> service.analyze(letter);
    }
}
