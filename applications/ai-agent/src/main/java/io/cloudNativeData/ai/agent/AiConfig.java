package io.cloudNativeData.ai.agent;

import io.cloudNativeData.ai.agent.properties.AgentProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.cloudNativeData.ai.web.domain.Letter;

import java.util.function.Function;

@Configuration
@EnableConfigurationProperties(AgentProperties.class)
public class AiConfig {

    /*
     Response to the following message. Keep answer short and concise.
            FROM: mom
            SUBJECT: I need

            A vacation


     */

    private final String prompt = """
            Response to the following message. Keep answer short and concise.
            Use less than 1000 words.
            
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
    Function<Letter,String> letterInference(ChatClient chatClient)
    {
        return letter -> {
                var letterResponse = chatClient.prompt()
                        .user(u -> u.text(letter.getBody())
                                .param("author", letter.getAuthor())
                                .param("subject", letter.getSubject())
                        )
                        .call();
                return letterResponse.content();
        };
    }
}
