package io.cloudNativeData.ai.services.nlp.sentiment;

import io.cloudNativeData.ai.web.domain.Letter;
import io.cloudNativeData.ai.web.domain.nlp.LetterSentiment;

/**
 * The Service to process sentiment
 * @author gregory green
 */
public interface LetterSentimentService {
    LetterSentiment analyze(Letter letter);
}
