package realtime.ai.ml.event.streaming.services.nlp.sentiment;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.io.IO;
import org.springframework.ai.embedding.EmbeddingOptions;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.util.List;
import java.util.Map;

@Slf4j
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


//        List<List<Double>> embeddings = model.embed(List.of(letter.getSubject()));

        EmbeddingOptions options = EmbeddingOptions.EMPTY;
        EmbeddingRequest request = new EmbeddingRequest(List.of(letter.getSubject()),options);
        var response = model.call(request);

        log.info("response: {}",response);

        //TODO: determine sentiment
        return LetterSentiment.builder().letter(letter).build();
    }
}
