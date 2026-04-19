package io.cloudNativeData.ai.agent.service;

import io.cloudNativeData.ai.agent.properties.AgentProperties;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import io.cloudNativeData.ai.web.domain.Letter;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AiAgentServiceTest {

    @Mock
    private Function<Letter,String> letterInference;
    private AiAgentService subject;

    private final Letter letter = JavaBeanGeneratorCreator
            .of(Letter.class).create();
    private final AgentProperties properties = AgentProperties.builder()
            .author("junit")
            .replySubjectPrefix("RE:").build();

    @BeforeEach
    void setUp() {
        subject = new AiAgentService(letterInference,properties);
    }

    @Test
    void inference() {
        var expected = Letter.builder()
                .receiver(letter.getAuthor())
                .author(properties.getAuthor())
                .body("expected")
                .subject(properties.getReplySubjectPrefix()+letter.getSubject())
                .build();

        when(letterInference.apply(any(Letter.class))).thenReturn(expected.getBody());

        var actual = subject.responseToLetter(letter);

        assertThat(actual).isEqualTo(expected);
    }
}