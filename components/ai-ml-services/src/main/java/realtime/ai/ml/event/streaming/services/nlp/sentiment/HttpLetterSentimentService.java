package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.net.URI;

@RequiredArgsConstructor
public class HttpLetterSentimentService implements LetterSentimentService{

    private final RestTemplate restTemplate;
    private final URI uri;

    @Override
    public LetterSentiment analyze(Letter letterSentiment) {
        return restTemplate.postForObject(uri, letterSentiment, LetterSentiment.class);
    }
}
