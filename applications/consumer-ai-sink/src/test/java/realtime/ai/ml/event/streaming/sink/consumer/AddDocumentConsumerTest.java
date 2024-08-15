package realtime.ai.ml.event.streaming.sink.consumer;

import nyla.solutions.core.patterns.conversion.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddDocumentConsumerTest {

    private AddDocumentConsumer subject;

    @Mock
    private VectorStore vectorStore;

    @Mock
    private Letter letter;

    @Mock
    private Converter<Letter, Document> converter;

    @Mock
    private Document document;

    @BeforeEach
    void setUp() {
        subject = new AddDocumentConsumer(vectorStore,converter);
    }

    @Test
    void addDocument() {

        when(converter.convert(any())).thenReturn(document);

        subject.accept(letter);

        verify(vectorStore).add(any());
    }
}