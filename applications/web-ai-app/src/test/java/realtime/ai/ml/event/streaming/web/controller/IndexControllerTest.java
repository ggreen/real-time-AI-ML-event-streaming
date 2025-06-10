package realtime.ai.ml.event.streaming.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    private IndexController subject;
    @Mock
    private Model model;
    private String userId = "junit";

    @BeforeEach
    void setUp() {
        subject = new IndexController(userId);
    }

    @Test
    void homePage() {
        var actual = subject.homePage(model);
        verify(model).addAttribute(anyString(),any());
        assertThat(actual).isEqualTo("index");
    }

    @Test
    void mailPage() {
        var actual = subject.mailPage(model);
        verify(model).addAttribute(anyString(),any());
        assertThat(actual).isEqualTo("mail");
    }
}