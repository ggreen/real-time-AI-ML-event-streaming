package realtime.ai.ml.event.streaming.sink;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2VectorsHttpService;

import java.net.URI;

@Profile("httpModel")
@Configuration
public class HttpModelConfig {

    @Value("${ai.model.uri}")
    private String uri;

    @SneakyThrows
    @Bean
    public Text2Vectors fakeText2Vectors()
    {
        return new Text2VectorsHttpService(new RestTemplate(),new URI(uri));
    }
}
