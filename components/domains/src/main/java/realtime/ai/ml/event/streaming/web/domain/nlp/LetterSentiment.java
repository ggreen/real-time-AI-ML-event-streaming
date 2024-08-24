package realtime.ai.ml.event.streaming.web.domain.nlp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterSentiment {
    private Letter letter;
    private double polarity;
    private SentimentType sentiment;
}
