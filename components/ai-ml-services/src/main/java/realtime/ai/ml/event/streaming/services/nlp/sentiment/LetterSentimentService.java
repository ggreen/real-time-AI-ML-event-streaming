package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import realtime.ai.ml.event.streaming.web.domain.Letter;

public interface LetterSentimentService {
    realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment analyze(Letter letterSentiment);
}
