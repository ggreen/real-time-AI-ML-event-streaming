package realtime.ai.ml.event.streaming.web.nlp.sentiment;

import lombok.SneakyThrows;
import nyla.solutions.core.data.collections.CapacityQueue;
import nyla.solutions.core.io.IO;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.indexing.NDArrayIndex;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SentimentAnalysisTest {

    public static String wordVectorsPath = "/Users/Projects/PlayGround/ai-ml/deeplearning4j/dev/deeplearning4j-examples/dl4j-examples/src/runtime/data/GoogleNews-vectors-negative300.bin.gz";
    private static SentimentDataSetIterator training ;
    private static SentimentDataSetIterator testing;
    private static int MAX_SIZE = 10;

    private SentimentAnalysis subject;
    private static WordVectors wordVectors = Mockito.mock(WordVectors.class);
    private static int batchSize = 100;
    private static VocabCache vocab = Mockito.mock(VocabCache.class);
    private static String firstWord = "first word";
    private static double[] vectors = {2.2,232.2,0.0};

    @BeforeAll
    static void beforeAll() {
//        when(wordVectors.hasWord(anyString())).thenReturn(true);
//        when(wordVectors.vocab()).thenReturn(vocab);
//        when(vocab.vocabExists()).thenReturn(true);
//        when(vocab.wordAtIndex(0)).thenReturn(firstWord);
//        when(wordVectors.getWordVector(anyString())).thenReturn(vectors);

        wordVectors = WordVectorSerializer.loadStaticModel(new File(wordVectorsPath));
        training =  constructTraining();
        testing =  constructTesting();
    }

    @BeforeEach
    void setUp() {



        subject = new SentimentAnalysis(wordVectors, training, testing);
    }



    @Test
    void training() throws Exception {


        int nEpochs = 1;
        int truncateReviewsToLength = 256;

        var model = subject.train();
//        System.out.println("Starting training");

//        model.save();

        String shortNegativeReview = "Boy, did that movie suck. It was like a bad version of my least favorite cartoon.";
        var test = subject.loadFeaturesFromString(shortNegativeReview, truncateReviewsToLength);
        evaluate(test, model, truncateReviewsToLength, shortNegativeReview);

        // another expected bad review
        String secondBadReview = "Homer - Yeah Moe that team sure did suck last night. They just plain sucked! I've seen teams suck before, but they were the suckiest bunch of sucks that ever sucked.";
        test = subject.loadFeaturesFromString(secondBadReview, truncateReviewsToLength);

        evaluate(test, model, truncateReviewsToLength, secondBadReview);

        // a good review follows (hopefully)
        String goodReview = "Boy, did I sure enjoy that movie.  It was great!";
        test = subject.loadFeaturesFromString(goodReview, truncateReviewsToLength);
        evaluate(test, model, truncateReviewsToLength, goodReview);

        System.out.println("----- Evaluation complete -----");

    }

    private void evaluate(INDArray features, MultiLayerNetwork model,
                          int truncateReviewsToLength, String review) throws IOException
    {
//        INDArray features = test.loadFeaturesFromString(review, truncateReviewsToLength);
        INDArray networkOutput = model.output(features);
        long timeSeriesLength = networkOutput.size(2);
        INDArray probabilitiesAtLastWord = networkOutput.get(NDArrayIndex.point(0), NDArrayIndex.all(), NDArrayIndex.point(timeSeriesLength - 1));

        System.out.println("\n\n-------------------------------");
        System.out.println("Review: \n" + review);
        System.out.println("\n\nProbabilities at last time step:");
        System.out.println("p(positive): " + probabilitiesAtLastWord.getDouble(0));
        System.out.println("p(negative): " + probabilitiesAtLastWord.getDouble(1));

        System.out.println("----- Evaluate complete -----");
    }

    @SneakyThrows
    private static SentimentDataSetIterator constructTesting() {

        String dir = "/Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/dl4j-ml-pipeline/runtime/data/aclImdb/test";

        List<SentimentData> sentiments = new ArrayList<>();

        int count =0;
        for(var file : IO.listFileRecursive(dir,"*.txt"))
        {
            count ++;
            if(count > MAX_SIZE)
                break;

            var sentimentData = new SentimentData();
            sentimentData.setText(IO.readFile(file));
            if(file.getAbsolutePath().contains("neg"))
                sentimentData.setPositive(false);
            else
                sentimentData.setPositive(true);

            sentimentData.setTokens(
                    SentimentAnalysis.tokens(sentimentData.getText(),wordVectors));

            sentiments.add(sentimentData);
        }

        return new SentimentDataSetIterator(wordVectors,batchSize,
                SentimentIteration.builder(sentiments.iterator())
                        .maxLength(256).size(sentiments.size())
                        .build());

    }

    @SneakyThrows
    private static SentimentDataSetIterator constructTraining() {
        String dir = "/Users/Projects/solutions/AI-ML/dev/real-time-AI-ML-event-streaming/applications/dl4j-ml-pipeline/runtime/data/aclImdb/train";

        CapacityQueue<SentimentData> sentiments = new CapacityQueue<>(new LinkedList<>(),MAX_SIZE);

        int count =0;

        for(var file : IO.listFileRecursive(dir,"*.txt"))
        {
            count ++;
            if(count > MAX_SIZE)
                break;

            var sentimentData = new SentimentData();
            sentimentData.setText(IO.readFile(file));
            if(file.getAbsolutePath().contains("neg"))
                sentimentData.setPositive(false);
            else
                sentimentData.setPositive(true);

            sentimentData.setTokens(
                    SentimentAnalysis.tokens(sentimentData.getText(),wordVectors));

            sentiments.add(sentimentData);
        }

        return new SentimentDataSetIterator(wordVectors,batchSize,
                SentimentIteration.builder(sentiments.iterator())
                        .maxLength(256).size(sentiments.size())
                        .build());
    }
}