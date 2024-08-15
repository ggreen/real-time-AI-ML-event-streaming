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
import realtime.ai.ml.event.streaming.web.domain.LetterResults;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LetterQueryControllerTest {

    private LetterQueryController subject;

    private String subjectText = "hello world";

    @Mock
    private VectorStore vectorStore;
    private LetterResults results = JavaBeanGeneratorCreator.of(LetterResults.class).create();

    @Mock
    private Document document;
    private List<Document> documents = asList(document);
    @Mock
    private Converter<Document, LetterResults> function;
    private double threshold = 0.33;

    @BeforeEach
    void setUp() {
        subject = new LetterQueryController(vectorStore,function,threshold);
    }

    @Test
    void searchBySubject() {

        when(vectorStore.similaritySearch(any(SearchRequest.class))).thenReturn(documents);
        when(function.convert(any())).thenReturn(results);

        List<LetterResults> expected = asList(results);

        var actual = subject.searchBySubject(subjectText);

        assertEquals(expected, actual);
    }
}