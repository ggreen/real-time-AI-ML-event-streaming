package realtime.ai.ml.event.streaming.web.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Letter {
    private String author;
    private String receiver;
    private String subject;
    private String body;
}
