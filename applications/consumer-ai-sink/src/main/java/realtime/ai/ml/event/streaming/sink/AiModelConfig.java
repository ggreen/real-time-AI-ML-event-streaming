package realtime.ai.ml.event.streaming.sink;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.Text2VectorsModel;
import realtime.ai.ml.event.streaming.services.conversions.DocumentToLetter;
import realtime.ai.ml.event.streaming.services.conversions.LetterToDocument;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;

@Configuration
public class AiModelConfig {

    @Bean
    EmbeddingModel embeddingModel(Text2Vectors text2Vectors)
    {
        return new Text2VectorsModel(text2Vectors);
    }

    @Bean
    Converter<Letter, Document> converter()
    {
        return new LetterToDocument();
    }
}
