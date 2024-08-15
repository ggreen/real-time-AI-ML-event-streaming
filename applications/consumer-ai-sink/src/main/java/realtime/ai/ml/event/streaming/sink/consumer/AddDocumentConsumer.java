package realtime.ai.ml.event.streaming.sink.consumer;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class AddDocumentConsumer {

    private final VectorStore vectorStore;
    private final Converter<Letter,Document> letterToDocument;

    public void accept(Letter letter) {

        vectorStore.add(Collections.singletonList(letterToDocument.convert(letter)));

    }
}
