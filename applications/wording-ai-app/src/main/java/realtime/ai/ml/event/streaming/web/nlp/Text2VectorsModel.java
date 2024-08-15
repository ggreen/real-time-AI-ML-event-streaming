package realtime.ai.ml.event.streaming.web.nlp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;
import realtime.ai.ml.event.streaming.web.nlp.vectors.Text2Vectors;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Text2VectorsModel implements EmbeddingModel {

    private final Text2Vectors text2Vectors;

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {

        var instructors = request.getInstructions();
        List<Embedding> embeddings = new ArrayList<>();
        Integer index = 0;
        for(String instructor : instructors){
            var document = new Document(instructor);
            var embedding = new Embedding(embed(document),index);
            embeddings.add(embedding);
        }
        return new EmbeddingResponse(embeddings);
    }

    @SneakyThrows
    @Override
    public List<Double> embed(Document document) {
        var text = document.getContent();

        return this.text2Vectors.convert(text);

    }


}
