package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.util.Text;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.domain.nlp.LetterSentiment;

import java.util.Map;

import static nyla.solutions.core.util.Organizer.toMap;

/**
 * Letter To Document
 * @author gregory green
 */
public class LetterSentimentToDocument implements Converter<LetterSentiment, Document> {

    /**
     *
     * @param letterSentiment the object to convert
     * @return the document
     */
    @Override
    public Document convert(LetterSentiment letterSentiment) {
        var letter = letterSentiment.getLetter();
        Map<String, Object> metadata = toMap(
                "receiver", letter.getReceiver(),
                "author", letter.getAuthor(),
                "body", letter.getBody(),
                "timeMs", letter.getTimeMs(),
                "sentiment", Text.toString(letterSentiment.getSentiment()),
                "polarity", letterSentiment.getPolarity()
                );

        return new Document(letter.getSubject(),metadata);
    }
}
