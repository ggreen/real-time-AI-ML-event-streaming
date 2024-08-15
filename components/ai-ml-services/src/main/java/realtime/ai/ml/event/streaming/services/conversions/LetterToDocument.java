package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.Map;

/**
 * Letter To Document
 * @author gregory green
 */
public class LetterToDocument implements Converter<Letter, Document> {

    /**
     *
     * @param letter the object to convert
     * @return the document
     */
    @Override
    public Document convert(Letter letter) {
        Map<String, Object> metadata = Map.of(
                "from",letter.getFrom(),
                "to",letter.getTo(),
                "body",letter.getBody());

        return new Document(letter.getSubject(),metadata);
    }
}
