package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.conversion.Converter;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Component;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;

import static java.lang.String.valueOf;

/**
 * Convert a document to a letter results
 * @author gregory green
 *
 */
public class DocumentToLetter implements Converter<Document, LetterResults> {
    @Override
    public LetterResults convert(Document document) {
        var metaData = document.getMetadata();

        var letterBuilder = Letter.builder();
        var letterResultsBuilder = LetterResults.builder();
        letterBuilder.subject(document.getContent());
        if(metaData != null && !metaData.isEmpty())
        {
            var distanceValue = metaData.get("distance");
            if(distanceValue != null)
                letterResultsBuilder.distance(Double.valueOf(valueOf(distanceValue)));

            letterBuilder.from(valueOf(metaData.get("from")))
                    .to(valueOf(metaData.get("to")))
                    .body(valueOf(metaData.get("body")));
        }

        return letterResultsBuilder.letter(letterBuilder.build()).build();
    }
}
