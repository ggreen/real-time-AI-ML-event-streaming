package realtime.ai.ml.event.streaming.sink;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Profile("valKey")
@Configuration
@EnableRedisRepositories
public class ValKeyConfig {
}
