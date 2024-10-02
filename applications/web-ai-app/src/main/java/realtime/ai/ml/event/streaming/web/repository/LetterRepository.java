package realtime.ai.ml.event.streaming.web.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

import java.util.List;

@Repository
public interface LetterRepository extends KeyValueRepository<LetterSentiment,String> {
    List<LetterSentiment> findByLetterReceiverOrderByLetterTimeMsDesc(String to, Limit limit);
}
