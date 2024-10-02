package realtime.ai.ml.event.streaming.sink.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.sink.repository.entity.LetterEntity;

@Repository
public interface LetterRepository extends CrudRepository<LetterEntity,String> {
}
