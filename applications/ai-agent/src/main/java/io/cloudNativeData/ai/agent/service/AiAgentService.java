package io.cloudNativeData.ai.agent.service;

import io.cloudNativeData.ai.agent.properties.AgentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiAgentService {
    private final Function<Letter,String> letterInference;
    private final AgentProperties properties;

    public Letter responseToLetter(Letter letter){
      log.info("Received {}. \n Waiting for a Response", letter);

      var response = letterInference.apply(letter);
      log.info("Response {}", response);

      return Letter.builder()
              .receiver(letter.getAuthor())
              .body(response)
              .author(properties.getAuthor())
              .subject(properties.getReplySubjectPrefix() + letter.getSubject())
              .build();
    }
}
