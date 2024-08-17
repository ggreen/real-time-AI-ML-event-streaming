package realtime.ai.ml.event.streaming.services.conversions;

import com.fasterxml.jackson.databind.ObjectMapper;
import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.util.Organizer;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.Map;

import static nyla.solutions.core.util.Organizer.toMap;

/**
 * Letter To Document
 * @author gregory green
 */
public class LetterToDocument implements Converter<Letter, Document> {

    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     *
     * @param letter the object to convert
     * @return the document
     */
    @Override
    public Document convert(Letter letter) {
        Map<String, Object> metadata = toMap(
                "author",letter.getAuthor(),
                "receiver",letter.getReceiver(),
                "body",letter.getBody(),
                "timeMillis",letter.getTimeMillis());

        return new Document(letter.getSubject(),metadata);
    }
}
