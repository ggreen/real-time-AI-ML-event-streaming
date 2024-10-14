package realtime.ai.ml.event.streaming.web.controller;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Limit;
import org.springframework.web.bind.annotation.*;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;
import realtime.ai.ml.event.streaming.web.repository.LetterRepository;
import realtime.ai.ml.event.streaming.web.repository.entity.LetterPost;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("letters/query")
@Slf4j
public class LetterQueryController {

    private final VectorStore vectorStore;
    private final double threshold;
    private final LetterRepository letterRepository;
    private final Converter<Document, LetterResults> toLetterResults;

    public LetterQueryController(VectorStore vectorStore,
                                 Converter<Document, LetterResults> toLetterResults,
                                 LetterRepository letterRepository,
                                 @Value("${ai.vector.similarity.search.threshold}")
                                 double threshold) {
        this.vectorStore = vectorStore;
        this.toLetterResults = toLetterResults;
        this.letterRepository = letterRepository;
        this.threshold = threshold;
    }

    @PostMapping("subject")
    @Cacheable("searchBySubject")
    public List<LetterResults> searchBySubject(@RequestParam String subjectText) {

        var documents = vectorStore.similaritySearch(SearchRequest.query(subjectText)
                .withSimilarityThreshold(threshold)
                .withTopK(5));

        return documents.stream().map((doc) -> toLetterResults.convert(doc)).collect(Collectors.toList());
    }

    @GetMapping("{receiver}/{limit}")
    @Cacheable("searchByReceiver")
    public List<LetterPost> searchByReceiver(@PathVariable String receiver, @PathVariable int limit) {

        return this.letterRepository.findByLetterReceiverOrderByLetterTimeMsDesc(receiver, Limit.of(limit));
    }


}
