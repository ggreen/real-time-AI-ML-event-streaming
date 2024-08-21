package realtime.ai.ml.event.streaming.processor.functions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterSentiment;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class LetterSentimentFunction implements Function<Letter,LetterSentiment> {

    private final LetterSentimentService service;
    public LetterSentiment apply(Letter letter) {
        return service.analyze(letter);
    }
}
