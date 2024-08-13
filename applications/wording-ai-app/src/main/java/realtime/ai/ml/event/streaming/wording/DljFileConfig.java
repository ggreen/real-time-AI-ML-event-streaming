package realtime.ai.ml.event.streaming.wording;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.File;

@Configuration
@Profile("dl4j-file")
public class DljFileConfig {

    @Value("${ai.model.word.vector.path}")
    private String wordVectorsPath;

    private WordVectors wordVectors;

    @Bean
    WordVectors wordVectorsCreator()
    {

        return WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
    }
}
