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
import realtime.ai.ml.event.streaming.domain.Letter;
import realtime.ai.ml.event.streaming.domain.LetterResults;
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
    private LetterResults letterResults = JavaBeanGeneratorCreator.of(LetterResults.class).create();

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
    private LetterPost letterPost = JavaBeanGeneratorCreator.of(LetterPost.class).create();
    private Limit limit = Limit.of(10);

    @BeforeEach
    void setUp() {
        this.letterPost.setLetter(this.letterResults.getLetter());

        subject = new LetterQueryController(vectorStore,toLetterResults,letterRepository,threshold);
    }

    @Test
    void searchBySubject() {

        when(vectorStore.similaritySearch(any(SearchRequest.class))).thenReturn(documents);
        when(toLetterResults.convert(any())).thenReturn(letterResults);

        List<LetterResults> expected = asList(letterResults);

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