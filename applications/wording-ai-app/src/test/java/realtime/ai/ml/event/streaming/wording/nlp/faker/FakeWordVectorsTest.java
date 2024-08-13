package realtime.ai.ml.event.streaming.wording.nlp.faker;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FakeWordVectorsTest {

    double[] expected = {0.3,0.4};

    private FakeWordVectors subject = new FakeWordVectors(expected);

    @Test
    void getWordVectorsMean() {
        Collection<String> input = asList("Testing");

        var actual = subject.getWordVectorsMean(input);

        assertEquals(expected, actual.toDoubleVector());
    }
}