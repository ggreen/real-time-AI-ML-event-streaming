package realtime.ai.ml.event.streaming.wording.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Letter {
    private String from;
    private String to;
    private String subject;
    private String body;
}
