package realtime.ai.ml.event.streaming.web;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import realtime.ai.ml.event.streaming.web.nlp.vectors.Dl4jWord2Vectors;

import java.io.File;

@Configuration
@Profile("dl4j")
public class Dl4jModelConfig {

    @Value("${ai.model.word.vector.path}")
    private String wordVectorsPath;

    private WordVectors wordVectors;

    @Bean
    WordVectors wordVectorsCreator()
    {

        return WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
    }

    @Bean
    public Dl4jWord2Vectors dl4jWord2Vectors(WordVectors word2Vec,
                            @Value("${spring.ai.vectorstore.pgvector.dimensions}")
                                             int dimensionLength) {
        return new Dl4jWord2Vectors(word2Vec,dimensionLength);

    }
}
