package io.cloudNativeData.ai.agent;

import io.cloudNativeData.ai.agent.service.AiAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Configuration
@Slf4j
public class AiConfig {

    /*
     Response to the following message. Keep answer short and concise.
            FROM: mom
            SUBJECT: I need

            A vacation


     */

    private final String prompt = """
            Response to the following message. Keep answer short and concise.
            FROM: <author>
            SUBJECT: <subject>
            
            <body>
            """;

    @Bean
    ChatClient chatClient(ChatModel chatModel) {

        return ChatClient
                .builder(chatModel)
                .defaultOptions(ChatOptions.builder()
                        .build())
                .build();

    }
    @Bean
    AiAgentService aiAgentService(ChatClient chatClient)
    {

        return letter -> {
            log.info("Received {}", letter);

                var letterResponse = chatClient.prompt()
                        .user(u -> u.text(letter.getBody())
                                .param("author", letter.getAuthor())
                                .param("subject", letter.getSubject())
                        )
                        .call()
                        .entity(Letter.class);

                log.info("****** Response: {}\n***************", letterResponse);

                return letterResponse;
        };
    }
}
