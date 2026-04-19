package io.cloudNativeData.ai.web.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.cloudNativeData.ai.web.repository.entity.LetterPost;

import java.util.List;

@Repository
public interface LetterRepository extends CrudRepository<LetterPost,String> {
    List<LetterPost> findByLetterReceiverOrderByLetterTimeMsDesc(String to, Limit limit);
}
