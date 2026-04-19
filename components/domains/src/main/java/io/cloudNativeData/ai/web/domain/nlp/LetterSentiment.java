package io.cloudNativeData.ai.web.domain.nlp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.cloudNativeData.ai.web.domain.Letter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LetterSentiment {
    private Letter letter;
    private double polarity;
    private SentimentType sentiment;
}
