package realtime.ai.ml.event.streaming.services.postgres.ml.sentiment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PgMlText2VectorsTest {

    private String text = "This is some text to embed";
    private PgMlText2Vectors subject;

    @Mock
    private JdbcTemplate jdbcTemplate;
    private double[] expectedVectors = {2.3,34.4};

    @BeforeEach
    void setUp() {
        subject = new PgMlText2Vectors(jdbcTemplate);
    }

    @Test
    void embeddings() {

        var actual = subject.convert(text);

        assertThat(actual).isNotNull();
    }
}