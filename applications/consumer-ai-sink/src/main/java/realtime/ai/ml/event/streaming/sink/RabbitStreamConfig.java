/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package realtime.ai.ml.event.streaming.sink;

import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.StreamCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
@Profile("streams")
public class RabbitStreamConfig {

    @Value("${consumer.ai.rabbit.stream.name:letter-sentiments.consumer-ai-sink}")
    private String streamName;

    @Value("${consumer.ai.rabbit.stream.max.size.gb:1}")
    private long maxSizeGb;

    @Bean
    StreamCreator stream(Environment environment)
    {
        var creator = environment.streamCreator()
                .stream(streamName)
                .maxLengthBytes(ByteCapacity.GB(maxSizeGb));

        creator.create();
        return creator;
    }
}
