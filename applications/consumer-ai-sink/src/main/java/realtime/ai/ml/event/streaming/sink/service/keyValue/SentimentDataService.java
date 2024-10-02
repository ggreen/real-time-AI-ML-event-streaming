package realtime.ai.ml.event.streaming.sink.service.keyValue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.sink.repository.LetterSentimentRepository;
import realtime.ai.ml.event.streaming.sink.service.SentimentService;

@Profile("valKey")
@RequiredArgsConstructor
@Service
public class SentimentDataService implements SentimentService {

    private final LetterSentimentRepository repository;

    @Override
    public void save(LetterSentiment letterSentiment) {
        repository.save(letterSentiment);
    }
}
