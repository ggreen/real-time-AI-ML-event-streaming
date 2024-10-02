package realtime.ai.ml.event.streaming.processor;

import nyla.solutions.core.io.IO;
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
    //"
    @Value("${ai.transformers.model.output.name:logits}")
    private String modelOutputName;


    @Bean
    TransformersEmbeddingModel transformersEmbeddingModel()
    {
        //distilbert/distilbert-base-uncased
        TransformersEmbeddingModel embeddingModel = new TransformersEmbeddingModel();

        // (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/tokenizer.json
        embeddingModel.setTokenizerResource(tokenizerUri);
//        embeddingModel.setTokenizerResource("file:///Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/sentiment-ai-processor/src/main/resources/distilbert-base-uncased/tokenizer.json");

// (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/model.onnx
        embeddingModel.setModelResource(modelUri);
//        embeddingModel.setModelResource("file:///Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/sentiment-ai-processor/src/main/resources/distilbert-base-uncased/model.onnx");

        // (optional) defaults to ${java.io.tmpdir}/spring-ai-onnx-model
        // Only the http/https resources are cached by default.
//
        embeddingModel.setResourceCacheDirectory(IO.tempDir()+"/text-classification");

        // (optional) Set the tokenizer padding if you see an errors like:
        // "ai.onnxruntime.OrtException: Supplied array is ragged, ..."
        //  embeddingModel.setTokenizerOptions(Map.of("padding", "true"));

        embeddingModel.setModelOutputName(modelOutputName);
        return embeddingModel;


    }


    @Bean
    LetterSentimentService transformModelLetterSentimentService(TransformersEmbeddingModel model)
    {
        return new TransformModelLetterSentimentService(model,tokenizerUri,modelUri);
    }
}
