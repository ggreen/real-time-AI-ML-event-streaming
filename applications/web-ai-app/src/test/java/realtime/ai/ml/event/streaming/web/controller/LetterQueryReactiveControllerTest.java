package realtime.ai.ml.event.streaming.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import realtime.ai.ml.event.streaming.web.repository.LetterRepository;

import java.util.concurrent.ThreadFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LetterQueryReactiveControllerTest {

    private LetterQueryReactiveController subject;

    @Mock
    private LetterRepository letterRepository;

    @Mock
    private ThreadFactory threadFactory;

    private String receiver = "user";

    @BeforeEach
    void setUp() {
        subject = new LetterQueryReactiveController(threadFactory,letterRepository,2,2);
    }

    @Test
    void streamLetters() {
        var actual = subject.streamLetters(receiver);

        assertThat(actual).isNotNull();
    }
}