package realtime.ai.ml.event.streaming.sink.repository.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import realtime.ai.ml.event.streaming.domain.Letter;
import realtime.ai.ml.event.streaming.domain.nlp.SentimentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LetterEntity {
    @Id
    private String Id;

    @Embedded
    private Letter letterSentiment;

    private double polarity;
    private SentimentType sentiment;

}
