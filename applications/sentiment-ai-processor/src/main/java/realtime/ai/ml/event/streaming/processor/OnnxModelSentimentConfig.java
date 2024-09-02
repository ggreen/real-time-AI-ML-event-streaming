package realtime.ai.ml.event.streaming.processor;

import org.springframework.ai.transformers.TransformersEmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.TransformModelLetterSentimentService;

@Configuration
@Profile("onnx.sentiment")
public class OnnxModelSentimentConfig {

    @Value("${ai.transformers.model.tokenizer.uri:classpath:/onnx/distilbert-base-uncased/tokenizer.json}")
    private String tokenizerUri;

    @Value("${ai.transformers.model.uri:classpath:/onnx/distilbert-base-uncased/model.onnx}")
    private String modelUri;

    @Bean
    LetterSentimentService transformModelLetterSentimentService(TransformersEmbeddingModel model)
    {
        return new TransformModelLetterSentimentService(model,tokenizerUri,modelUri);
    }
}
