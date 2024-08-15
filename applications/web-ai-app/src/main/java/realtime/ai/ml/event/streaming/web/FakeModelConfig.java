package realtime.ai.ml.event.streaming.web;

import nyla.solutions.core.util.collections.DimensionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import realtime.ai.ml.event.streaming.web.nlp.vectors.Text2Vectors;

import java.util.List;

@Profile("fakeModel")
@Configuration
public class FakeModelConfig {

    private double[] vectors = {  -0.0025,   -0.0007,   -0.0121,    0.0118};

    @Value("${spring.ai.vectorstore.pgvector.dimensions}")
    private int dimensionsLength;

    @Bean
    public Text2Vectors fakeText2Vectors()
    {
        return text -> DimensionBuilder.builder(vectors).fillValue(0.0).length(dimensionsLength).build();
    }
}
