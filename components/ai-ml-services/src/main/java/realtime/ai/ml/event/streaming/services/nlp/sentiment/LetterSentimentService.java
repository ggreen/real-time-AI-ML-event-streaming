package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

/**
 * The Service to process sentiment
 * @author gregory green
 */
public interface LetterSentimentService {
    LetterSentiment analyze(Letter letter);
}
