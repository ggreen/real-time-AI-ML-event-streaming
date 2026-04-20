package io.cloudNativeData.ai.agent.properties;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@ConfigurationProperties("agent")
@Builder
@Data
public class AgentProperties{
    private String author;
    private String replySubjectPrefix;
}
