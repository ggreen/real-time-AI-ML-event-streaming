package realtime.ai.ml.event.streaming.sink;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.conversions.LetterSentimentToDocument;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

@Configuration
public class AiAppConfig {

    @Bean
    Converter<LetterSentiment, Document> converter()
    {
        return new LetterSentimentToDocument();
    }

}
