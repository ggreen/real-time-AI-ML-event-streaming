package realtime.ai.ml.event.streaming.web.nlp.vectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

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
    private Double[] expected = {0.23,0.23};
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