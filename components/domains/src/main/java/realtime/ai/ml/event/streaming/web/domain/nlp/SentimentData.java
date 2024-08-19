package realtime.ai.ml.event.streaming.web.domain.nlp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SentimentData {

    private String text;
    private boolean positive;
    private List<String> tokens;

}
