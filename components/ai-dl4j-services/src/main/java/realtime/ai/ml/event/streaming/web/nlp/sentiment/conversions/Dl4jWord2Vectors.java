package realtime.ai.ml.event.streaming.web.nlp.sentiment.conversions;

import nyla.solutions.core.util.collections.DimensionBuilder;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
public class Dl4jWord2Vectors implements Function<String,List<Double>> {

    private final WordVectors word2Vec;
    private final int dimensionLength;

    public Dl4jWord2Vectors(WordVectors word2Vec,
                            int dimensionLength) {
        this.word2Vec = word2Vec;
        this.dimensionLength = dimensionLength;
    }


    @Override
    public List<Double> apply(String text) {
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
