package realtime.ai.ml.event.streaming.sink.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

@Repository
@NoRepositoryBean
public interface LetterSentimentRepository {
    LetterSentiment save(LetterSentiment letterSentiment);
}
