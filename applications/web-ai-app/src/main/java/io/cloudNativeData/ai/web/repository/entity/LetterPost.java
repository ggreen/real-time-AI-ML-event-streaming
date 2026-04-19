package io.cloudNativeData.ai.web.repository.entity;

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
@Table(name="letter_entity")
public class LetterPost {
    @Id
    private String Id;

    @Embedded
    @AttributeOverride(
            name = "body",
            column = @Column(length = 2000)
    )
    private Letter letter;

    private double polarity;

    private SentimentType sentiment;
}
