package realtime.ai.ml.event.streaming.web.nlp.vectors;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Dl4JWord2VectorsTest {
    private Dl4jWord2Vectors subject;
    private String text = "Hello world";
    private double[] input = {2.2,232.2};

    private List<Double> expected = Arrays.asList(2.2,232.2,0.0);


    private int dimensionLength = 3;

    @Mock
    private WordVectors wordVectors;

    @Mock
    private INDArray tensor;

    @BeforeEach
    void setUp() {
        subject = new Dl4jWord2Vectors(wordVectors,dimensionLength);
    }

    @Test
    void convert() {

        when(wordVectors.getWordVectorsMean(any())).thenReturn(tensor);
        when(tensor.toDoubleVector()).thenReturn(input);

        var actual = subject.apply(text);

        assertThat(actual).isEqualTo(expected);
    }
}