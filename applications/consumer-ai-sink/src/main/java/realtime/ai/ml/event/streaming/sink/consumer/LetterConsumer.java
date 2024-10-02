package realtime.ai.ml.event.streaming.sink.consumer;

import lombok.RequiredArgsConstructor;
import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import realtime.ai.ml.event.streaming.domain.Letter;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.sink.service.SentimentService;

import java.util.Collections;
import java.util.function.Consumer;

import static java.lang.String.valueOf;

@Component
@RequiredArgsConstructor
public class LetterConsumer implements Consumer<Message<LetterSentiment>> {

    private final VectorStore vectorStore;
    private final SentimentService letterRepository;
    private final Converter<LetterSentiment,Document> letterToDocument;

    @Transactional
    public void accept(Message<LetterSentiment> letterMessage) {

        var letterSentiment = letterMessage.getPayload();
        var letter = letterSentiment.getLetter();
        prepareLetter(letter);

        vectorStore.add(Collections.singletonList(letterToDocument.convert(letterSentiment)));
        var id = valueOf(letterMessage.getHeaders().get("id"));
        letterSentiment.setId(id);

//        var letterEntity = LetterEntity.builder().Id(id).letterSentiment(letter)
//                .polarity(letterSentiment.getPolarity())
//                .sentiment(letterSentiment.getSentiment())
//                .build();

        letterRepository.save(letterSentiment);

    }

    /**
     *
     * @param letterSentiment the letter
     * @return the create letter
     */
    void prepareLetter(Letter letterSentiment) {
        if(letterSentiment.getTimeMs() == null || letterSentiment.getTimeMs().equals(0L))
            letterSentiment.setTimeMs(System.currentTimeMillis());
    }
}
