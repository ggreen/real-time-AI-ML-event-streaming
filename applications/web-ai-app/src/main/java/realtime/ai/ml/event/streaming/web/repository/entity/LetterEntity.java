package realtime.ai.ml.event.streaming.web.repository.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class LetterEntity {
    @Id
    private String Id;

    @Embedded
    private Letter letter;
}
