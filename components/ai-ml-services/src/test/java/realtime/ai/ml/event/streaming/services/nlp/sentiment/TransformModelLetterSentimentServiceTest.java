package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.transformers.TransformersEmbeddingModel;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransformModelLetterSentimentServiceTest {

    private TransformModelLetterSentimentService subject;
    @Mock
    private TransformersEmbeddingModel model;
    @Mock
    private EmbeddingResponse expectedResponse;
    private LetterSentiment expected = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
    private String tokenizerUri = "";
    private String modelUri = "";
    private Embedding expectedEmbedding;

    @BeforeEach
    void setUp() {
        subject = new TransformModelLetterSentimentService(model,tokenizerUri,modelUri);
    }

//    TODO @Test
    void analyze() {

        when(model.call(any())).thenReturn(expectedResponse);
        when(expectedResponse.getResult()).thenReturn(expectedEmbedding);
//        when(expectedEmbedding.getOutput()).thenReturn(expectedOutput);

        var actual = subject.analyze(expected.getLetter());
        assertThat(actual).isEqualTo(expected);
    }
}