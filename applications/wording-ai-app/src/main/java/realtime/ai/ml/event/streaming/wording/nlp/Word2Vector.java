package realtime.ai.ml.event.streaming.wording.nlp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.creational.Creator;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class Word2Vector implements EmbeddingModel {

    private final Creator<WordVectors> wordVectorsCreator;

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

    @Override
    public List<Double> embed(Document document) {
        var word2Vec = wordVectorsCreator.create();

        // Convert a string to a vector
        var vector = word2Vec.getWordVectorsMean(Arrays.asList(document.getContent().split(" ")));
//        var vector = word2Vec.getWordVectorMatrixNormalized(document.getContent());


        log.info("Vectors: {}",vector);
        return DoubleStream.of(vector.toDoubleVector()).boxed().collect(Collectors.toList());
    }
}
