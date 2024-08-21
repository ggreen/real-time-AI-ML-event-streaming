package realtime.ai.ml.event.streaming.web.nlp.sentiment;

import org.jetbrains.annotations.NotNull;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentData;

import java.util.Iterator;

public class SentimentIteration implements Iterable<SentimentData>{

    private final Iterator<SentimentData> iterator;
    private final int size;
    private final int maxLength;

    public SentimentIteration(Iterator<SentimentData> iterator, int size, int maxLength) {
        this.iterator = iterator;
        this.size = size;
        this.maxLength = maxLength;
    }

    public static SentimentIterationBuilder builder(Iterator<SentimentData> iterator) {
        return new SentimentIterationBuilder(iterator);
    }

    @NotNull
    @Override
    public Iterator<SentimentData> iterator() {
        return iterator;
    }

    public int size() {
        return size;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public static class SentimentIterationBuilder {

        private final Iterator<SentimentData> iterator;
        private int size;
        private int maxLength;

        public SentimentIterationBuilder(Iterator<SentimentData> iterator) {
            this.iterator = iterator;
        }

        public SentimentIterationBuilder size(int size) {
            this.size = size;
            return this;
        }

        public SentimentIterationBuilder maxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public SentimentIteration build() {
            return new SentimentIteration(iterator,size,maxLength);
        }
    }
}
