package realtime.ai.ml.event.streaming.web.nlp.vectors;

import nyla.solutions.core.util.collections.DimensionBuilder;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;

import java.util.Arrays;
import java.util.List;

public class Dl4jWord2Vectors implements Text2Vectors{

    private final WordVectors word2Vec;
    private final int dimensionLength;

    public Dl4jWord2Vectors(WordVectors word2Vec,
//                            @Value("${spring.ai.vectorstore.pgvector.dimensions}")
                            int dimensionLength) {
        this.word2Vec = word2Vec;
        this.dimensionLength = dimensionLength;
    }


    @Override
    public List<Double> convert(String text) {
        // Convert a string to a vector
        var vector = word2Vec.getWordVectorsMean(Arrays.asList(text.split(" ")));

        System.out.println(vector);

        var vectors = vector.toDoubleVector();

        return DimensionBuilder.builder(vectors)
                .fillValue(0.0)
                .length(dimensionLength)
                .build();
    }
}
