package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterSentiment;

import java.net.URI;

@RequiredArgsConstructor
public class HttpLetterSentimentService implements LetterSentimentService{

    private final RestTemplate restTemplate;
    private final URI uri;

    @Override
    public LetterSentiment analyze(Letter letter) {
        return restTemplate.postForObject(uri,letter,LetterSentiment.class);
    }
}
