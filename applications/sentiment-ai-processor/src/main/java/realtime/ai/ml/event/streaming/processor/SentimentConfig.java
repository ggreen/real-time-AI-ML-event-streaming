package realtime.ai.ml.event.streaming.processor;

import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.TransformModelLetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.util.function.Function;

@Configuration
public class SentimentConfig {


    @Value("${ai.transformers.model.tokenizer.uri:classpath:/onnx/distilbert-base-uncased/tokenizer.json}")
    private String tokenizerUri;

    @Value("${ai.transformers.model.uri:classpath:/onnx/distilbert-base-uncased/model.onnx}")
    private String modelUri;

    @Bean
    LetterSentimentService transformModelLetterSentimentService(TransformersEmbeddingModel model)
    {
        return new TransformModelLetterSentimentService(model,tokenizerUri,modelUri);
    }

    @Bean
    Function<Letter, LetterSentiment> letterToSentiment(LetterSentimentService service) {
        return letter -> service.analyze(letter);
    }
}
