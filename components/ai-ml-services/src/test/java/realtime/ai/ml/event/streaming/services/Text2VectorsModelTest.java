package realtime.ai.ml.event.streaming.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingRequest;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Text2VectorsModelTest {

    private String text = "Hello world";
    private Text2VectorsModel subject;

    @Mock
    private Text2Vectors wordVectors;


    @Mock
    private EmbeddingRequest request;
    private int length = 5;

    @BeforeEach
    void setUp() {
        subject = new Text2VectorsModel(wordVectors);
    }

    @Test
    void toVector() {

        List<Double> expected = Arrays.asList(0.34);

        when(wordVectors.convert(any())).thenReturn(expected);

        var actual = subject.embed(new Document(text));

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    void call() {

        List<Double> expected = Arrays.asList(0.34);
        double[] vectors = {0.34};

        List<String> instructors = Arrays.asList("Testing");
        when(request.getInstructions()).thenReturn(instructors);
        when(wordVectors.convert(any())).thenReturn(expected);


        var actual = subject.call(request);

        assertThat( actual).isNotNull();
        var results = actual.getResults();

        assertThat(results.get(0).getOutput().get(0)).isEqualTo(expected.get(0));
    }
}