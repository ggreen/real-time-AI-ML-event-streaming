package realtime.ai.ml.event.streaming.web.nlp.sentiment;

import nyla.solutions.core.util.Organizer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.api.InvocationType;
import org.deeplearning4j.optimize.listeners.EvaluativeListener;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.indexing.NDArrayIndex;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import realtime.ai.ml.event.streaming.domain.nlp.SentimentData;

import java.io.IOException;
import java.util.List;

public class SentimentAnalysis {

    private TokenizerFactory tokenizerFactory;
    private final WordVectors wordVectors;
    private final  SentimentDataSetIterator train;
    private final SentimentDataSetIterator test;

    public SentimentAnalysis(WordVectors wordVectors, SentimentDataSetIterator train, SentimentDataSetIterator test)
    {
        this.wordVectors = wordVectors;
        this.train = train;
        this.test = test;
        tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        //DataSetIterators for training and testing respectively
    }


    /**
     * Data URL for downloading
     */
//    public static final String DATA_URL = "http://ai.stanford.edu/~amaas/data/sentiment/aclImdb_v1.tar.gz";
    /**
     * Location to save and extract the training/testing data
     */
//    public static final String DATA_PATH = "/Users/Projects/PlayGround/ai-ml/deeplearning4j/dev/deeplearning4j-examples/dl4j-examples/src/runtime/data/";
    //FilenameUtils.concat(System.getProperty("java.io.tmpdir"), "dl4j_w2vSentiment/");
    /**
     * Location (local file system) for the Google News vectors. Set this manually.
     * https://drive.google.com/file/d/0B7XkCwpI5KDYNlNUTTlSS21pQmM/edit?resourcekey=0-wjGZdNAUop6WykTtMip30g
     * GoogleNews-vectors-negative300.bin.gz
     */
//    public static String wordVectorsPath = "/Users/Projects/PlayGround/ai-ml/deeplearning4j/dev/deeplearning4j-examples/dl4j-examples/src/runtime/data/GoogleNews-vectors-negative300.bin.gz";


    public MultiLayerNetwork train() throws Exception {


        int batchSize = 64;     //Number of examples in each minibatch
        int vectorSize = 300;   //Size of the word vectors. 300 in the Google News model
        int nEpochs = 1;        //Number of epochs (full passes of training data) to train on
        int truncateReviewsToLength = 256;  //Truncate reviews with length (# words) greater than this
        final int seed = 0;     //Seed for reproducibility

        Nd4j.getMemoryManager().setAutoGcWindow(10000);  //https://deeplearning4j.konduit.ai/config/config-memory/config-workspaces#garbage-collector

        //Set up network configuration
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .updater(new Adam(5e-3))
                .l2(1e-5)
                .weightInit(WeightInit.XAVIER)
                .gradientNormalization(GradientNormalization.ClipElementWiseAbsoluteValue).gradientNormalizationThreshold(1.0)
                .list()
                .layer(new LSTM.Builder().nIn(vectorSize).nOut(256)
                        .activation(Activation.TANH).build())
                .layer(new RnnOutputLayer.Builder().activation(Activation.SOFTMAX)
                        .lossFunction(LossFunctions.LossFunction.MCXENT).nIn(256).nOut(2).build())
                .build();

        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        System.out.println("Starting training");
        model.setListeners(new ScoreIterationListener(1), new EvaluativeListener(test, 1, InvocationType.EPOCH_END));
        model.fit(train, nEpochs);


        return model;

//        System.out.println("Starting training");
//        net.setListeners(new ScoreIterationListener(1), new EvaluativeListener(test, 1, InvocationType.EPOCH_END));
//        net.fit(train, nEpochs);
//
//        net.save(file);
//
////        model.save();
//
//        String shortNegativeReview = "Boy, did that movie suck. It was like a bad version of my least favorite cartoon.";
//        evaluate(test, model, truncateReviewsToLength, shortNegativeReview);
//
//        // another expected bad review
//        String secondBadReview = "Homer - Yeah Moe that team sure did suck last night. They just plain sucked! I've seen teams suck before, but they were the suckiest bunch of sucks that ever sucked.";
//        evaluate(test, model, truncateReviewsToLength, secondBadReview);
//
//        // a good review follows (hopefully)
//        String goodReview = "Boy, did I sure enjoy that movie.  It was great!";
//        evaluate(test, model, truncateReviewsToLength, goodReview);
//
//        System.out.println("----- Evaluation complete -----");
//        return 0;

    }

    private SentimentIteration constructTestingData(WordVectors wordVectors) {

        SentimentData s1 = new SentimentData();
        s1.setText("I hate this movie");
        s1.setPositive(false);
        s1.setTokens(tokenizerFactory.create(s1.getText()).getTokens());

        SentimentData s2 = new SentimentData();
        s2.setText("I love this movie");
        s2.setPositive(true);
        s2.setTokens(tokenizerFactory.create(s1.getText()).getTokens());

        List<SentimentData> list = Organizer.toList(s1,s2);

        return SentimentIteration.builder(list.iterator()).maxLength(100).size(2).build();
    }


    public INDArray loadFeaturesFromString(String review, int truncateReviewsToLength){
        return test.loadFeaturesFromString(review, truncateReviewsToLength);
    }

    private SentimentIteration constructTrainingData(WordVectors wordVectors) {
        SentimentData s1 = new SentimentData();
        s1.setText("I hate this movie");
        s1.setPositive(false);
        s1.setTokens(tokenizerFactory.create(s1.getText()).getTokens());

        SentimentData s2 = new SentimentData();
        s2.setText("I love this movie");
        s2.setPositive(true);
        s2.setTokens(tokenizerFactory.create(s1.getText()).getTokens());

        List<SentimentData> list = Organizer.toList(s1,s2);

        return SentimentIteration.builder(list.iterator()).maxLength(100).size(2).build();
    }

        /**
         * After training: load a single example and generate predictions
         * @param test
         * @param model
         * @param truncateReviewsToLength
         * @param review
         * @throws IOException
         */
        private void evaluate(SentimentDataSetIterator test, MultiLayerNetwork model,
        int truncateReviewsToLength, String review) throws IOException
        {
            INDArray features = test.loadFeaturesFromString(review, truncateReviewsToLength);
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

    public static List<String> tokens(String text, WordVectors wordVectors) {
        var tokenizerFactory = new DefaultTokenizerFactory();
        tokenizerFactory.setTokenPreProcessor(new CommonPreprocessor());

        //Filter to stop words
        return  tokenizerFactory.create(text).getTokens().stream()
                .filter(wordVectors::hasWord).toList();
    }
}
