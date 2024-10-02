package realtime.ai.ml.event.streaming.sink.service;

import org.springframework.stereotype.Service;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

@Service
public interface SentimentService {
    void save(LetterSentiment letterSentiment);
}
