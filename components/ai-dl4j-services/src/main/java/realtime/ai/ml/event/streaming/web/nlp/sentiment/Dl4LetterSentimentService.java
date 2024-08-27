package realtime.ai.ml.event.streaming.web.nlp.sentiment;


import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.springframework.stereotype.Service;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Dl4LetterSentimentService implements LetterSentimentService {
    private final MultiLayerNetwork model;
    private final TokenizerFactory tokenizerFactory;

    private final WordVectors wordVectors;
    private final long vectorSize  = 100;

    public Dl4LetterSentimentService(MultiLayerNetwork model, WordVectors wordVectors) {
        this.model = model;
        this.wordVectors = wordVectors;

        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
    }

    @Override
    public realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment analyze(Letter letterSentiment) {

        INDArray networkOutput = toFeature(letterSentiment);
        long timeSeriesLength = networkOutput.size(2);
        INDArray probabilitiesAtLastWord = networkOutput.get(NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(timeSeriesLength - 1));

        log.info("Review: {}", letterSentiment.getSubject());
        log.info("Probabilities at last time step:");
        log.info("probabilitiesAtLastWord: {}",probabilitiesAtLastWord);
        double positiveProbability = probabilitiesAtLastWord.getDouble(0);
        double negativeProbability = probabilitiesAtLastWord.getDouble(1);


        var builder = realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment.builder().letter(letterSentiment);

        if(positiveProbability > negativeProbability)
            builder.sentiment(SentimentType.POSITIVE);
        else
            builder.sentiment(SentimentType.NEGATIVE);

        return builder.build();



    }

    public INDArray toFeature(Letter letterSentiment) {

        List<String> tokens = tokenizerFactory.create(letterSentiment.getSubject()).getTokens();
        List<String> tokensFiltered = new ArrayList<>();
        for(String t : tokens ){
            if(wordVectors.hasWord(t)) tokensFiltered.add(t);
        }
        int outputLength = tokensFiltered.size();

        INDArray features = Nd4j.create(1, vectorSize, outputLength);

        for( int j=0; j<tokensFiltered.size() ; j++ ){
            String token = tokensFiltered.get(j);
            INDArray vector = wordVectors.getWordVectorMatrix(token);
            if(vector == null){
                continue;   //Word not in word vectors
            }
            features.put(new INDArrayIndex[]{NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(j)}, vector);
        }

        return features;
    }
}
