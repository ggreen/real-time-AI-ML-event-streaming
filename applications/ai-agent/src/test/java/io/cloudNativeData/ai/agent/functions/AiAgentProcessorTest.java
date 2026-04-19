package io.cloudNativeData.ai.agent.functions;

import io.cloudNativeData.ai.agent.service.AiAgentService;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.cloudNativeData.ai.web.domain.Letter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AiAgentProcessorTest {


    private final Letter letter = JavaBeanGeneratorCreator.of(Letter.class).create();
    private final Letter expected = JavaBeanGeneratorCreator.of(Letter.class).create();

    @Mock
    private AiAgentService service;

    private AiAgentProcessor subject;

    @BeforeEach
    void setUp() {
        subject = new AiAgentProcessor(service);
    }

    @Test
    void given_letter_when_apply_then_call_ai_service() {

        when(service.responseToLetter(any())).thenReturn(expected);

        var actual = subject.apply(letter);
        assertThat(expected).isEqualTo(actual);

    }
}