package io.cloudNativeData.ai.agent.service;

import realtime.ai.ml.event.streaming.web.domain.Letter;

@FunctionalInterface
public interface AiAgentService {
    Letter responseToLetter(Letter letter);
}
