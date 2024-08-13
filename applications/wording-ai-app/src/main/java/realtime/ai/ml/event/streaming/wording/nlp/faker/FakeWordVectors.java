package realtime.ai.ml.event.streaming.wording.nlp.faker;

import org.deeplearning4j.models.embeddings.WeightLookupTable;
import org.deeplearning4j.models.embeddings.reader.ModelUtils;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.wordstore.VocabCache;
import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FakeWordVectors implements WordVectors {
    private final double[] fakeDouble;

    public FakeWordVectors(double[] fakeDouble) {
        this.fakeDouble = fakeDouble;
    }

    @Override
    public String getUNK() {
        return "";
    }

    @Override
    public void setUNK(String newUNK) {

    }

    @Override
    public boolean hasWord(String word) {
        return false;
    }

    @Override
    public Collection<String> wordsNearest(INDArray words, int top) {
        return List.of();
    }

    @Override
    public Collection<String> wordsNearestSum(INDArray words, int top) {
        return List.of();
    }

    @Override
    public Collection<String> wordsNearestSum(String word, int n) {
        return List.of();
    }

    @Override
    public Collection<String> wordsNearestSum(Collection<String> positive, Collection<String> negative, int top) {
        return List.of();
    }

    @Override
    public Map<String, Double> accuracy(List<String> questions) {
        return Map.of();
    }

    @Override
    public int indexOf(String word) {
        return 0;
    }

    @Override
    public List<String> similarWordsInVocabTo(String word, double accuracy) {
        return List.of();
    }

    @Override
    public double[] getWordVector(String word) {
        return new double[0];
    }

    @Override
    public INDArray getWordVectorMatrixNormalized(String word) {
        return null;
    }

    @Override
    public INDArray getWordVectorMatrix(String word) {
        return null;
    }

    @Override
    public INDArray getWordVectors(Collection<String> labels) {
        return null;
    }

    @Override
    public INDArray getWordVectorsMean(Collection<String> labels) {
        return new FakeINDArray(fakeDouble);
    }

    @Override
    public Collection<String> wordsNearest(Collection<String> positive, Collection<String> negative, int top) {
        return List.of();
    }

    @Override
    public Collection<String> wordsNearest(String word, int n) {
        return List.of();
    }

    @Override
    public double similarity(String word, String word2) {
        return 0;
    }

    @Override
    public VocabCache vocab() {
        return null;
    }

    @Override
    public WeightLookupTable lookupTable() {
        return null;
    }

    @Override
    public void setModelUtils(ModelUtils utils) {

    }

    @Override
    public boolean outOfVocabularySupported() {
        return false;
    }

    @Override
    public void loadWeightsInto(INDArray indArray) {

    }

    @Override
    public long vocabSize() {
        return 0;
    }

    @Override
    public int vectorSize() {
        return 0;
    }

    @Override
    public boolean jsonSerializable() {
        return false;
    }
}
