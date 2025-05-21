package realtime.ai.ml.event.streaming.services.nlp.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.net.URI;

/**
 * Generate the sentiment of text using a REST service
 * @author gregory green
 */
@RequiredArgsConstructor
public class HttpLetterSentimentService implements LetterSentimentService{

    private final RestTemplate restTemplate;
    private final URI uri;

    /**
     * Process sentiment
     * @param letterSentiment the text content wrapper
     * @return the sentiment
     */
    @Override
    public LetterSentiment analyze(Letter letterSentiment) {
        return restTemplate.postForObject(uri, letterSentiment, LetterSentiment.class);
    }
}
