package realtime.ai.ml.event.streaming.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetterResults {
    private Letter letter;
    private double distance;
    private double polarity;
    private SentimentType sentiment;
}
