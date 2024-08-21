package realtime.ai.ml.event.streaming.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterSentiment {
    private Letter letter;
    private Sentiment sentiment;
}
