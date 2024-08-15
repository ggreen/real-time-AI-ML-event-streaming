package realtime.ai.ml.event.streaming.web.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("letters/query")
public class LetterQueryController {

    private final VectorStore vectorStore;
    private final double threshold;
    private final Converter<Document, LetterResults> toLetter;

    public LetterQueryController(VectorStore vectorStore,
                                 Converter<Document, LetterResults> toLetter,
                                 @Value("${ai.vector.similarity.search.threshold}")
                                 double threshold) {
        this.vectorStore = vectorStore;
        this.toLetter = toLetter;
        this.threshold = threshold;
    }

    @PostMapping("subject")
    public List<LetterResults> searchBySubject(@RequestParam String subjectText) {

        var documents = vectorStore.similaritySearch(SearchRequest.query(subjectText)
                .withSimilarityThreshold(threshold)
                .withTopK(5));

        return documents.stream().map((doc) -> toLetter.convert(doc)).collect(Collectors.toList());
    }
}
