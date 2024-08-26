package realtime.ai.ml.event.streaming.services.postgres.ml.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;
import realtime.ai.ml.event.streaming.web.domain.nlp.SentimentType;

@RequiredArgsConstructor
public class PostgresMlLetterService implements LetterSentimentService {

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
    public LetterSentiment analyze(Letter letter) {
        var sentiments = jdbcTemplate.query(sentimentQuery,
                (rs, i) ->
                LetterSentiment.builder()
                        .letter(letter)
                        .polarity(rs.getDouble("score"))
                        .sentiment(toSentiment(rs.getString("label")))
                        .build(),
                letter.getSubject());

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
