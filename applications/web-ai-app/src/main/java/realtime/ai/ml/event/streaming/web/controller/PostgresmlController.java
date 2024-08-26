package realtime.ai.ml.event.streaming.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.postgresml.PostgresMlEmbeddingModel;
import org.springframework.ai.postgresml.PostgresMlEmbeddingOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("postgres")
@RequiredArgsConstructor
public class PostgresmlController {

    private final EmbeddingModel embeddingModel;

    @PostMapping
    EmbeddingResponse t(@RequestBody String text) {

        return embeddingModel.call(
                new EmbeddingRequest(List.of(text),
                        PostgresMlEmbeddingOptions.builder()
                                .withTransformer("text-classification")
                                .withMetadataMode(MetadataMode.ALL)
                                .withVectorType(PostgresMlEmbeddingModel.VectorType.PG_ARRAY)
//                                .withKwargs(Map.of("device", "cpu"))
                                .build()));
    }
}
