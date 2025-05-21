package realtime.ai.ml.event.streaming.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.embedding.EmbeddingModel;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmbeddingControllerTest {

    private String text = "I love my wife";
    private EmbeddingController subject;
    @Mock
    private EmbeddingModel model;
    private float[] expected = {2.3F,2.3F};

    @BeforeEach
    void setUp() {
        subject = new EmbeddingController(model);
    }

    @Test
    void embedded() {
        when(model.embed(text)).thenReturn(expected);
        var actual = subject.embed(text);

        assertEquals(expected, actual);
    }
}