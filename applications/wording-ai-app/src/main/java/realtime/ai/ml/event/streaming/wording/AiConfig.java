package realtime.ai.ml.event.streaming.wording;

import nyla.solutions.core.patterns.creational.Creator;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AiConfig {
    @Value("${ai.model.word.vector.path}")
    private String wordVectorsPath;
    private WordVectors wordVectors;

    @Bean
    Creator<WordVectors> wordVectorsCreator()
    {
        return () -> {
            if(wordVectors == null)
                wordVectors = WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
            return wordVectors;
        };
    }
}
