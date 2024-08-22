package realtime.ai.ml.event.streaming.web;

import lombok.SneakyThrows;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.services.Text2VectorsModel;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;
import realtime.ai.ml.event.streaming.web.nlp.sentiment.Dl4LetterSentimentService;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

@Configuration
public class Dl4jModelConfig {

    @Value("${ai.model.word.vector.path}")
    private String wordVectorsPath;

    private WordVectors wordVectors;

    @Bean
    EmbeddingModel embeddingModel(Function<String, List<Double>> converter)
    {
        return new Text2VectorsModel(text -> converter.apply(text));
    }

    @Bean
    WordVectors wordVectorsCreator()
    {

        return WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
    }

    @Bean
    Dl4LetterSentimentService dl4LetterSentimentService(MultiLayerNetwork model, WordVectors word2Vec)
    {
        return new Dl4LetterSentimentService(model,word2Vec);
    }

    @SneakyThrows
    @Bean
    MultiLayerNetwork model(@Value("${ai.mode.file.path}") String filePath)
    {
        return MultiLayerNetwork.load(Paths.get(filePath).toFile(),true);
    }
}
