package io.cloudNativeData.ai.services.postgres.ml.sentiment;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import io.cloudNativeData.ai.services.nlp.vectors.vectors.Text2Vectors;

@RequiredArgsConstructor
public class PgMlText2Vectors implements Text2Vectors {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public float[] convert(String sourceObject) {
        return new float[0];
    }
}
