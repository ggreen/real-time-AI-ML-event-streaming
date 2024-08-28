package realtime.ai.ml.event.streaming.web.repository.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="letter_entity")
public class LetterPost {
    @Id
    private String Id;

    @Embedded
    private Letter letter;

    private double polarity;

    private SentimentType sentiment;
}
