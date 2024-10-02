package realtime.ai.ml.event.streaming.sink;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Profile({"postgres"})
@Configuration
@EnableJpaRepositories
public class JdbcConfig {
}
