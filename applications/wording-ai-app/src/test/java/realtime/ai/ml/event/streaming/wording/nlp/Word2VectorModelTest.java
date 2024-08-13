package realtime.ai.ml.event.streaming.wording.nlp;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingRequest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Word2VectorModelTest {

    private String text = "Hello world";
    private Word2VectorModel subject;

    @Mock
    private WordVectors wordVectors;


    @Mock
    private INDArray tensor;

    @Mock
    private EmbeddingRequest request;
    private int length = 5;

    @BeforeEach
    void setUp() {
        subject = new Word2VectorModel(wordVectors,length);
    }

    @Test
    void toVector() {

        double[] expected = {0.34};

        when(wordVectors.getWordVectorsMean(any())).thenReturn(tensor);
        when(tensor.toDoubleVector()).thenReturn(expected);

        var actual = subject.embed(new Document(text));

        assertEquals(expected[0], actual.get(0));

    }

    @Test
    void call() {

        double[] vectors = {0.34};

        List<String> instructors = Arrays.asList("Testing");
        when(request.getInstructions()).thenReturn(instructors);
        when(wordVectors.getWordVectorsMean(any())).thenReturn(tensor);
        when(tensor.toDoubleVector()).thenReturn(vectors);


        var actual = subject.call(request);

        assertThat( actual).isNotNull();
        var results = actual.getResults();

        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getOutput().size()).isEqualTo(length);
    }
}