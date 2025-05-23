package realtime.ai.ml.event.streaming.services.nlp.vectors.vectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2VectorsHttpService;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Text2VectorsHttpServiceTest {
    private Text2VectorsHttpService subject;

    @Mock
    private RestTemplate restTemplate;
    private String text = "Hello World";
    private float[] expected = {0.23F,0.23F};
    private URI uri;

    @BeforeEach
    void setUp() throws URISyntaxException {
        uri = new URI("file://tmp/notUsed");
        subject = new Text2VectorsHttpService(restTemplate,uri);
    }

    @Test
    void convert() {
        when(restTemplate.postForObject(any(URI.class),anyString(),any(Class.class))).thenReturn(expected);
        var actual = subject.convert(text);
        assertThat(actual).contains(expected[0]);
    }
}