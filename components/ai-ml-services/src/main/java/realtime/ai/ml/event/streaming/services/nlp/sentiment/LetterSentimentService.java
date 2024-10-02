package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import realtime.ai.ml.event.streaming.domain.Letter;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

public interface LetterSentimentService {
    LetterSentiment analyze(Letter letter);
}
