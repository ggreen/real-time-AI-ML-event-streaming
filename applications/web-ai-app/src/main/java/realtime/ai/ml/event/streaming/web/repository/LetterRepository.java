package realtime.ai.ml.event.streaming.web.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import realtime.ai.ml.event.streaming.web.repository.entity.LetterPost;

import java.util.List;

@Repository
public interface LetterRepository extends CrudRepository<LetterPost,String> {
    List<LetterPost> findByLetterReceiverOrderByLetterTimeMsDesc(String to, Limit limit);
}
