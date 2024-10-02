package realtime.ai.ml.event.streaming.sink.consumer;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import realtime.ai.ml.event.streaming.sink.service.SentimentService;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterSentimentConsumerTest {

    private LetterConsumer subject;

    @Mock
    private VectorStore vectorStore;


    @Mock
    private SentimentService sentimentService;

    private LetterSentiment letterSentiment;

    @Mock
    private Converter<LetterSentiment, Document> converter;

    @Mock
    private Document document;
    @Mock
    private Message<LetterSentiment> letterMsg;
    @Mock
    private MessageHeaders headers;

    @BeforeEach
    void setUp() {
        letterSentiment = JavaBeanGeneratorCreator.of(LetterSentiment.class).create();
        subject = new LetterConsumer(vectorStore, sentimentService,converter);
    }

    @Test
    void addLetter() {

        when(letterMsg.getPayload()).thenReturn(letterSentiment);
        when(letterMsg.getHeaders()).thenReturn(headers);
        when(converter.convert(any())).thenReturn(document);

        subject.accept(letterMsg);

        verify(vectorStore).add(any());
        verify(sentimentService).save(any());
    }


    @Test
    void prepareLetter() {

         subject.prepareLetter(letterSentiment.getLetter());

        assertThat(letterSentiment.getLetter().getTimeMs()).isGreaterThan(0L);
    }
}