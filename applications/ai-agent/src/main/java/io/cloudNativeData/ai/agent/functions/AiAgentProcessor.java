package io.cloudNativeData.ai.agent.functions;

import io.cloudNativeData.ai.agent.service.AiAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import realtime.ai.ml.event.streaming.web.domain.Letter;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiAgentProcessor implements Function<Letter,Letter> {
    private final AiAgentService agent;

    @Override
    public Letter apply(Letter letter) {

        log.info("Input: {}",letter);
        var response = agent.responseToLetter(letter);

        log.info("Response: {}",response);

        return response;

    }
}
