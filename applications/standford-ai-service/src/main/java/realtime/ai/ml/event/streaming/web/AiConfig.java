package realtime.ai.ml.event.streaming.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.stanford.sentiment.StanfordNlpSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.function.Function;

@Configuration
public class AiConfig {

    @Value("${ai.model.word.vector.path}")
    private String wordVectorsPath;

    @Bean
    LetterSentimentService letterSentimentService()
    {
        return new StanfordNlpSentimentService();
    }

    @Bean
    Function<Letter, realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment> letterToLetterSentiment(LetterSentimentService service)
    {
        return letter -> service.analyze(letter);
    }
}
