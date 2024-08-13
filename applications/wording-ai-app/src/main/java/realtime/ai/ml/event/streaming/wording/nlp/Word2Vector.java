package realtime.ai.ml.event.streaming.wording.nlp;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.util.collections.DimensionBuilder;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Word2Vector implements EmbeddingModel {


    private final  WordVectors word2Vec;
    private final int dimensionLength;

    public Word2Vector(WordVectors word2Vec,
                       @Value("${spring.ai.vectorstore.pgvector.dimensions}")
                       int dimensionLength) {
        this.word2Vec = word2Vec;
        this.dimensionLength = dimensionLength;
    }

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

        // Convert a string to a vector
        var vector = word2Vec.getWordVectorsMean(Arrays.asList(document.getContent().split(" ")));

        System.out.println(vector);

        var vectors = vector.toDoubleVector();

        return DimensionBuilder.builder(vectors)
                .fillValue(0.0)
                .length(dimensionLength)
                .build();
    }


}
