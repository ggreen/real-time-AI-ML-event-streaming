package realtime.ai.ml.event.streaming.services.conversions;

import nyla.solutions.core.patterns.conversion.Converter;
import nyla.solutions.core.patterns.conversion.numbers.TextToDouble;
import nyla.solutions.core.patterns.conversion.numbers.TextToLong;
import nyla.solutions.core.util.Text;
import org.springframework.ai.document.Document;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.LetterResults;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

/**
 * Convert a document to a letter results
 * @author gregory green
 *
 */
public class DocumentToLetterResults implements Converter<Document, LetterResults> {
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
                letterResultsBuilder.distance(Double.valueOf(Text.toString(distanceValue)));

            letterBuilder.author(Text.toString(metaData.get("author")))
                    .receiver(Text.toString(metaData.get("receiver")))
                    .body(Text.toString(metaData.get("body")));

            letterBuilder
                    .timeMs(TextToLong.fromObject(metaData.get("timeMs"),0L));


            letterResultsBuilder.polarity(TextToDouble.fromObject(metaData.get("polarity"),0L));


            var sentimentText = Text.toString(metaData.get("sentiment"));

            if(sentimentText == null || sentimentText.isEmpty())
                letterResultsBuilder.sentiment(SentimentType.NEUTRAL);
            else
                letterResultsBuilder.sentiment(SentimentType.valueOf(sentimentText ));
        }

        return letterResultsBuilder.letter(letterBuilder.build()).build();
    }
}
