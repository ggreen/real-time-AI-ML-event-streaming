package io.cloudNativeData.ai.services.postgres.ml.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import io.cloudNativeData.ai.services.nlp.sentiment.LetterSentimentService;
import io.cloudNativeData.ai.web.domain.Letter;
import io.cloudNativeData.ai.web.domain.nlp.LetterSentiment;
import io.cloudNativeData.ai.web.domain.nlp.SentimentType;

@RequiredArgsConstructor
public class PgMlLetterSentimentService implements LetterSentimentService {

    private final JdbcTemplate jdbcTemplate;
    private String sentimentQuery = """
            select sentiment.positivity::json->0->>'label' as "label",
            sentiment.positivity::json->0->'score' as "score"
            from
            (SELECT pgml.transform(
                task   => 'text-classification',
                inputs => ARRAY[
                    ?
                ]
            ) AS positivity) sentiment;
            """;

    @Override
    public LetterSentiment analyze(Letter letterSentiment) {
        var sentiments = jdbcTemplate.query(sentimentQuery,
                (rs, i) ->
                    LetterSentiment.builder()
                        .letter(letterSentiment)
                        .polarity(rs.getDouble("score"))
                        .sentiment(toSentiment(rs.getString("label")))
                        .build(),
                letterSentiment.getBody());

        if(sentiments == null || sentiments.isEmpty())
            return null;

        return sentiments.iterator().next();
    }

    protected SentimentType toSentiment(String label) {

        if("POSITIVE".equalsIgnoreCase(label))
            return SentimentType.POSITIVE;
        else if("NEGATIVE".equalsIgnoreCase(label))
            return SentimentType.NEGATIVE;

        return SentimentType.NEUTRAL;
    }
}
