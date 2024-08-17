package realtime.ai.ml.event.streaming.sink.consumer;

import nyla.solutions.core.patterns.conversion.Converter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.messaging.Message;
import realtime.ai.ml.event.streaming.sink.repository.LetterRepository;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterConsumerTest {

    private LetterConsumer subject;

    @Mock
    private VectorStore vectorStore;


    @Mock
    private LetterRepository letterRepository;

    @Mock
    private Letter letter;

    @Mock
    private Converter<Letter, Document> converter;

    @Mock
    private Document document;
    @Mock
    private Message<Letter> letterMsg;

    @BeforeEach
    void setUp() {
        subject = new LetterConsumer(vectorStore,letterRepository,converter);
    }

    @Test
    void addLetter() {

        when(letterMsg.getPayload()).thenReturn(letter);
        when(converter.convert(any())).thenReturn(document);

        subject.accept(letterMsg);

        verify(vectorStore).add(any());
        verify(letterRepository).save(any());
    }
}