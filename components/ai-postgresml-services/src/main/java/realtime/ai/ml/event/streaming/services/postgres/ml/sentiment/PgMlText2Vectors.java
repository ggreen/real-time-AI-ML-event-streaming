package realtime.ai.ml.event.streaming.services.postgres.ml.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import realtime.ai.ml.event.streaming.services.nlp.vectors.vectors.Text2Vectors;

import java.util.List;

@RequiredArgsConstructor
public class PgMlText2Vectors implements Text2Vectors {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public float[] convert(String sourceObject) {
        return new float[0];
    }
}
