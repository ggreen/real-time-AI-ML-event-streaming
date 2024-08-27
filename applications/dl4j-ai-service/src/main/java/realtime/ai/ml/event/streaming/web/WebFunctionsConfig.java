package realtime.ai.ml.event.streaming.web;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.nlp.sentiment.conversions.Dl4jWord2Vectors;

import java.util.List;
import java.util.function.Function;

@Configuration
public class WebFunctionsConfig {
    @Bean
    Function<String, List<Double>> dl4jWord2Vectors(WordVectors word2Vec,
                                                    @Value("${spring.ai.vectorstore.pgvector.dimensions}")
                                                     int dimensionLength) {
        return new Dl4jWord2Vectors(word2Vec,dimensionLength);

    }

    @Bean
    Function<Letter, realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment> d4lLetterSentiment(LetterSentimentService letterSentimentService)
    {
        return letter -> letterSentimentService.analyze(letter);
    }
}
