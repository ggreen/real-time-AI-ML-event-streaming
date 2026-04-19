package io.cloudNativeData.realtime.ai.ml.event.streaming.sink.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.cloudNativeData.ai.web.domain.Letter;
import io.cloudNativeData.ai.web.domain.nlp.SentimentType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LetterEntity {
    @Id
    private String Id;

    @Embedded
    @AttributeOverride(
            name = "body",
            column = @Column(length = 2000)
    )
    private Letter letterSentiment;

    private double polarity;
    private SentimentType sentiment;

}
