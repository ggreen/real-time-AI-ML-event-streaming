package realtime.ai.ml.event.streaming.sink.consumer;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import realtime.ai.ml.event.streaming.sink.repository.LetterRepository;
import realtime.ai.ml.event.streaming.sink.repository.entity.LetterEntity;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.Collections;
import java.util.function.Consumer;

import static java.lang.String.valueOf;

@Component
@RequiredArgsConstructor
public class LetterConsumer implements Consumer<Message<Letter>> {

    private final VectorStore vectorStore;
    private final LetterRepository letterRepository;
    private final Converter<Letter,Document> letterToDocument;

    @Transactional
    public void accept(Message<Letter> letterMessage) {

        var letter = letterMessage.getPayload();

        vectorStore.add(Collections.singletonList(letterToDocument.convert(letter)));
        var id = valueOf(letterMessage.getHeaders().get("id"));

        var letterEntity = LetterEntity.builder().Id(id).letter(letter).build();
        letterRepository.save(letterEntity);

    }
}
