package realtime.ai.ml.event.streaming.processor;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.HttpLetterSentimentService;
import realtime.ai.ml.event.streaming.services.nlp.sentiment.LetterSentimentService;
import realtime.ai.ml.event.streaming.services.postgres.ml.sentiment.PostgresMlLetterService;
import realtime.ai.ml.event.streaming.web.domain.Letter;
import realtime.ai.ml.event.streaming.web.domain.nlp.LetterSentiment;

import java.net.URI;
import java.util.function.Function;

@Configuration
public class SentimentConfig {


    @Bean
    RestTemplate restTemplate()
    {
        return new RestTemplate();
    }

    @Bean
    LetterSentimentService letterSentimentService(JdbcTemplate jdbcTemplate)
    {
        return new PostgresMlLetterService(jdbcTemplate);
    }

//    @SneakyThrows
//    @Bean
//    LetterSentimentService letterSentimentService(RestTemplate restTemplate,
//                                                  @Value("${ai.sentiment.service.uri}") String uri)
//    {
//        return new HttpLetterSentimentService(restTemplate,new URI(uri));
//    }

    @Bean
    Function<Letter,LetterSentiment> letterToSentiment(LetterSentimentService service) {
        return letter -> service.analyze(letter);
    }
}
