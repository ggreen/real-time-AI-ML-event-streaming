package io.cloudNativeData.ai.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.cloudNativeData.ai.web.repository.LetterRepository;

import java.util.concurrent.ThreadFactory;

import static org.assertj.core.api.Assertions.assertThat;

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