package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterSentiment;

public interface LetterSentimentService {
    LetterSentiment analyze(Letter letter);
}