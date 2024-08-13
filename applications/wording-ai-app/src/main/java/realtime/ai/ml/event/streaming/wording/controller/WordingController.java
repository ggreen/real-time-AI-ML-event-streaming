package realtime.ai.ml.event.streaming.wording.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wording")
public class WordingController {

    private final VectorStore vectorStore;
    private final double threshold;

    public WordingController(VectorStore vectorStore,
                             @Value("${ai.vector.similarity.search.threshold}") double threshold) {
        this.vectorStore = vectorStore;
        this.threshold = threshold;
    }


    @PostMapping("document")
    public void addDocument(@RequestBody List<Document> documents) {
        // Add the documents to PGVector
        vectorStore.add(documents);
    }

    @GetMapping("query")
    public List<Document> search(@RequestParam String text){

        // Retrieve documents similar to a query
        return vectorStore.similaritySearch(SearchRequest.query(text).withSimilarityThreshold(threshold).withTopK(5));
    }


}
