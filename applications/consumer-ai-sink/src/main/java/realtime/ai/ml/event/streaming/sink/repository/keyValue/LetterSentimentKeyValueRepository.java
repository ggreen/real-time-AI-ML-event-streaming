package realtime.ai.ml.event.streaming.sink.repository.keyValue;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.sink.repository.LetterSentimentRepository;

@Repository
public interface LetterSentimentKeyValueRepository extends KeyValueRepository<LetterSentiment,String>, LetterSentimentRepository {
}
