package realtime.ai.ml.event.streaming.web;

import nyla.solutions.core.util.Organizer;
import nyla.solutions.core.util.collections.DimensionBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;

import java.util.List;

@Profile("fakeModel")
@Configuration
public class FakeModelConfig {

    private Float[] vectors = {  -0.0025F,   -0.0007F,   -0.0121F,    0.0118F};

    @Value("${spring.ai.vectorstore.pgvector.dimensions}")
    private int dimensionsLength;

    @Bean
    public Text2Vectors fakeText2Vectors()
    {
        return text ->{
            List<Float> list = DimensionBuilder.builder(vectors)
                    .fillValue(0.0)
                    .length(dimensionsLength).build();
            float[] array = new float[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i); // auto-unboxing Float to float
            }
            return array;
        };
    }
}
