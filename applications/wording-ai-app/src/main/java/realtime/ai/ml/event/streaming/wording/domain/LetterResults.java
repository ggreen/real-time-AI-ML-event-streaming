package realtime.ai.ml.event.streaming.wording.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetterResults {
    private Letter letter;
    private double distance;
}
