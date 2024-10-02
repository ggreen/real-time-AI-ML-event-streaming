package realtime.ai.ml.event.streaming.web.nlp.sentiment;

import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.DataSetPreProcessor;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.INDArrayIndex;
import org.nd4j.linalg.indexing.NDArrayIndex;
import realtime.ai.ml.event.streaming.domain.nlp.SentimentData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SentimentDataSetIterator implements DataSetIterator {

    private final WordVectors wordVectors;
    private  final SentimentIteration sentimentIteration;
    private final int vectorSize;
    private final int totalOutcomes = 2;
    private final int batchSize;
    private int cursor = 0;
    private final Iterator<SentimentData> iterator;
    private final TokenizerFactory tokenizerFactory;

    public SentimentDataSetIterator(WordVectors wordVectors, int batchSize,
                                    SentimentIteration sentimentIterable) {
        this.wordVectors = wordVectors;
        this.sentimentIteration = sentimentIterable;
        VocabCache vocab = wordVectors.vocab();
        if(vocab == null)
            throw new IllegalArgumentException("vocab is required");
        if(!vocab.vocabExists())
            throw new IllegalArgumentException("vocab.vocabExists() is required");

        String firstWord = vocab.wordAtIndex(0);

        this.vectorSize = wordVectors.getWordVector(firstWord).length;
        this.batchSize = batchSize;
        this.iterator = sentimentIterable.iterator();

        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());
    }

    @Override
    public DataSet next(final int numberOfExamples) {
        INDArray features = Nd4j.create(new int[]{sentimentIteration.size(), vectorSize, sentimentIteration.getMaxLength()}, 'f');
        INDArray labels = Nd4j.create(new int[]{sentimentIteration.size(), totalOutcomes, sentimentIteration.getMaxLength()}, 'f');    //Two labels: positive or negative

        //Because we are dealing with reviews of different lengths and only one output at the final time step: use padding arrays
        //Mask arrays contain 1 if data is present at that time step for that example, or 0 if data is just padding
        INDArray featuresMask = Nd4j.zeros(sentimentIteration.size(), sentimentIteration.getMaxLength());
        INDArray labelsMask = Nd4j.zeros(sentimentIteration.size(), sentimentIteration.getMaxLength());

        int i = 0;
        while( iterator.hasNext() && i< numberOfExamples && cursor< sentimentIteration.size() ){
            SentimentData sourceData = iterator.next();
            List<String> tokens = sourceData.getTokens();

            // Get the truncated sequence length of document (i)
            int seqLength = Math.min(tokens.size(), sentimentIteration.getMaxLength());

            // Get all wordvectors for the current document and transpose them to fit the 2nd and 3rd feature shape
            final INDArray vectors = wordVectors.getWordVectors(tokens.subList(0, seqLength)).transpose();

            // Put wordvectors into features array at the following indices:
            // 1) Document (i)
            // 2) All vector elements which is equal to NDArrayIndex.interval(0, vectorSize)
            // 3) All elements between 0 and the length of the current sequence
            features.put(
                    new INDArrayIndex[] {
                            NDArrayIndex.point(i), NDArrayIndex.all(), NDArrayIndex.interval(0, seqLength)
                    },
                    vectors);

            // Assign "1" to each position where a feature is present, that is, in the interval of [0, seqLength)
            featuresMask.get(new INDArrayIndex[] {NDArrayIndex.point(i), NDArrayIndex.interval(0, seqLength)}).assign(1);

            int idx = (sourceData.isPositive() ? 0 : 1);
            int lastIdx = Math.min(tokens.size(), sentimentIteration.getMaxLength());
            labels.putScalar(new int[]{i,idx,lastIdx-1},1.0);   //Set label: [0,1] for negative, [1,0] for positive
            labelsMask.putScalar(new int[]{i,lastIdx-1},1.0);   //Specify that an output exists at the final time step for this example

            i++;
            cursor++;
        }


        return new DataSet(features,labels,featuresMask,labelsMask);
    }

    @Override
    public int inputColumns() {
        return vectorSize;
    }

    @Override
    public int totalOutcomes() {
        return totalOutcomes;
    }

    @Override
    public boolean resetSupported() {
        return true;
    }

    @Override
    public boolean asyncSupported() {
        return false;
    }

    @Override
    public void reset() {
        cursor = 0;
    }

    @Override
    public int batch() {
        return batchSize;
    }

    @Override
    public void setPreProcessor(DataSetPreProcessor preProcessor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public DataSetPreProcessor getPreProcessor() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> getLabels() {
        return Arrays.asList("positive","negative");
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public DataSet next() {
        return next(batchSize);
    }

    /**
     * Used post training to convert a String to a features INDArray that can be passed to the network output method
     *
     * @param reviewContents Contents of the review to vectorize
     * @param maxLength Maximum length (if review is longer than this: truncate to maxLength). Use Integer.MAX_VALUE to not nruncate
     * @return Features array for the given input String
     */
    public INDArray loadFeaturesFromString(String reviewContents, int maxLength){
        List<String> tokens = tokenizerFactory.create(reviewContents).getTokens();
        List<String> tokensFiltered = new ArrayList<>();
        for(String t : tokens ){
            if(wordVectors.hasWord(t)) tokensFiltered.add(t);
        }
        int outputLength = Math.min(maxLength,tokensFiltered.size());

        INDArray features = Nd4j.create(1, vectorSize, outputLength);

        int count = 0;
        for( int j=0; j<tokensFiltered.size() && count<maxLength; j++ ){
            String token = tokensFiltered.get(j);
            INDArray vector = wordVectors.getWordVectorMatrix(token);
            if(vector == null){
                continue;   //Word not in word vectors
            }
            features.put(new INDArrayIndex[]{NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(j)}, vector);
            count++;
        }

        return features;
    }
}
