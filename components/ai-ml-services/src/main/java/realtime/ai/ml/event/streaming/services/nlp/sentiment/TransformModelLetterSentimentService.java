package realtime.ai.ml.event.streaming.services.nlp.sentiment;


import lombok.SneakyThrows;
import nyla.solutions.core.io.IO;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import realtime.ai.ml.event.streaming.domain.Letter;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

import java.util.List;

public class TransformModelLetterSentimentService  implements LetterSentimentService{
    private final TransformersEmbeddingModel model;
    private final String tokenizerUri;
    private final String modelUri;

    public TransformModelLetterSentimentService(TransformersEmbeddingModel model,
//                                                @Value("${ai.transformers.model.tokenizer.uri}")
                                                String tokenizerUri,
//                                                @Value("${ai.transformers.model.uri}")
                                                String modelUri) {
        this.model = model;
        this.tokenizerUri = tokenizerUri;
        this.modelUri = modelUri;
    }


    @SneakyThrows
    @Override
    public LetterSentiment analyze(Letter letter) {

        //distilbert/distilbert-base-uncased
        TransformersEmbeddingModel embeddingModel = new TransformersEmbeddingModel();

        // (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/tokenizer.json
        //embeddingModel.setTokenizerResource("classpath:/onnx/distilbert-base-uncased/tokenizer.json");
        embeddingModel.setTokenizerResource("file:///Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/sentiment-ai-processor/src/main/resources/distilbert-base-uncased/tokenizer.json");

// (optional) defaults to classpath:/onnx/all-MiniLM-L6-v2/model.onnx
        //embeddingModel.setModelResource("classpath:/onnx/distilbert-base-uncased/model.onnx");
        embeddingModel.setModelResource("file:///Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/sentiment-ai-processor/src/main/resources/distilbert-base-uncased/model.onnx");

    // (optional) defaults to ${java.io.tmpdir}/spring-ai-onnx-model
    // Only the http/https resources are cached by default.
        embeddingModel.setResourceCacheDirectory(IO.tempDir()+"/text-classification");


    // (optional) Set the tokenizer padding if you see an errors like:
    // "ai.onnxruntime.OrtException: Supplied array is ragged, ..."
    //  embeddingModel.setTokenizerOptions(Map.of("padding", "true"));

        List<List<Double>> embeddings = embeddingModel.embed(List.of(letter.getSubject()));

        return LetterSentiment.builder().letter(letter).build();
    }
}
