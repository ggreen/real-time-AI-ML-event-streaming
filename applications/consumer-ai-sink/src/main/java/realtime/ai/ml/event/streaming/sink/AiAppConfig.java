package realtime.ai.ml.event.streaming.sink;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.conversions.LetterSentimentToDocument;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

@Configuration
public class AiAppConfig {

//    @Bean
//    EmbeddingModel embeddingModel(Text2Vectors text2Vectors)
//    {
//        return new Post(text2Vectors);
//    }

    @Bean
    Converter<LetterSentiment, Document> converter()
    {
        return new LetterSentimentToDocument();
    }

}
