package realtime.ai.ml.event.streaming.web.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;
import realtime.ai.ml.event.streaming.web.repository.LetterRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("letters/query")
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
    public List<LetterResults> searchBySubject(@RequestParam String subjectText) {

        var documents = vectorStore.similaritySearch(SearchRequest.query(subjectText)
                .withSimilarityThreshold(threshold)
                .withTopK(5));

        return documents.stream().map((doc) -> toLetterResults.convert(doc)).collect(Collectors.toList());
    }

    @GetMapping("{to}")
    public List<Letter> searchByTo(@PathVariable String to) {

        return this.letterRepository.findByLetterTo(to);
    }
}
