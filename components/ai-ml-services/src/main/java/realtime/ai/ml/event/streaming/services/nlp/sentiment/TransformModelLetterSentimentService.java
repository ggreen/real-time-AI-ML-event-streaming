package realtime.ai.ml.event.streaming.services.nlp.sentiment;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.util.List;

@Slf4j
public class TransformModelLetterSentimentService  implements LetterSentimentService{
    private final TransformersEmbeddingModel model;
    private final String tokenizerUri;
    private final String modelUri;

    public TransformModelLetterSentimentService(TransformersEmbeddingModel model,
                                                String tokenizerUri,
                                                String modelUri) {
        this.model = model;
        this.tokenizerUri = tokenizerUri;
        this.modelUri = modelUri;
    }


    @SneakyThrows
    @Override
    public LetterSentiment analyze(Letter letter) {

        EmbeddingRequest request = new EmbeddingRequest(List.of(letter.getSubject()),null);
        var response = model.call(request);

        log.info("response: {}",response);

        //TODO: determine sentiment
        return LetterSentiment.builder().letter(letter).build();
    }
}
