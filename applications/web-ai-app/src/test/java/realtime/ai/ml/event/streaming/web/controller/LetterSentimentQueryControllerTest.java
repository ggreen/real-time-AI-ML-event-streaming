package realtime.ai.ml.event.streaming.web.controller;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.creational.generator.JavaBeanGeneratorCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.data.domain.Limit;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;
import realtime.ai.ml.event.streaming.web.repository.entity.LetterPost;
import realtime.ai.ml.event.streaming.web.repository.LetterRepository;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterSentimentQueryControllerTest {

    private LetterQueryController subject;

    private String subjectText = "hello world";

    @Mock
    private VectorStore vectorStore;
    @Mock
    private LetterRepository letterRepository;
    private LetterResults results = JavaBeanGeneratorCreator.of(LetterResults.class).create();

    @Mock
    private Document document;
    private List<Document> documents = asList(document);
    @Mock
    private Converter<Document, LetterResults> toLetterResults;

    @Mock
    private Converter<Document, Letter> toLetter;

    private double threshold = 0.33;
    private String to = "user";
    private String id = "id";
    private LetterPost letterPost = new LetterPost(id,results.getLetter());
    private Limit limit = Limit.of(10);

    @BeforeEach
    void setUp() {
        subject = new LetterQueryController(vectorStore,toLetterResults,letterRepository,threshold);
    }

    @Test
    void searchBySubject() {

        when(vectorStore.similaritySearch(any(SearchRequest.class))).thenReturn(documents);
        when(toLetterResults.convert(any())).thenReturn(results);

        List<LetterResults> expected = asList(results);

        var actual = subject.searchBySubject(subjectText);

        assertEquals(expected, actual);
    }

    @Test
    void listByTo() {

        List<LetterPost> expected =  asList(letterPost);
        when(letterRepository.findByLetterReceiverOrderByLetterTimeMsDesc(to,limit)).thenReturn(expected);

        List<LetterPost> actual = subject.searchByReceiver(to,limit.max());

        assertEquals(expected, actual);
    }
}