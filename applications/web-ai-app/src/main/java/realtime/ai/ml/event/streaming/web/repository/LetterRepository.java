package realtime.ai.ml.event.streaming.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.repository.entity.LetterEntity;

import java.util.List;

@Repository
public interface LetterRepository extends CrudRepository<LetterEntity,String> {
    List<Letter> findByLetterTo(String to);
}
