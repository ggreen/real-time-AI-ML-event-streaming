/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package realtime.ai.ml.event.streaming.web;

import com.rabbitmq.client.amqp.Environment;
import com.rabbitmq.client.amqp.impl.AmqpEnvironmentBuilder;
import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbitmq.client.AmqpConnectionFactory;
import org.springframework.amqp.rabbitmq.client.RabbitAmqpAdmin;
import org.springframework.amqp.rabbitmq.client.RabbitAmqpTemplate;
import org.springframework.amqp.rabbitmq.client.SingleAmqpConnectionFactory;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;



    @Value("${letter.exchange:letters}")
    private String letterExchange;


    @Bean
    Environment amqpEnvironment() {
        var env = new AmqpEnvironmentBuilder()
                .connectionSettings()
                .environmentBuilder()
                .build();

        env.connectionBuilder().name(applicationName);
        return env;
    }

    @Bean
    RabbitAmqpAdmin admin(AmqpConnectionFactory connectionFactory) {
        var admin = new RabbitAmqpAdmin(connectionFactory);
        admin.declareExchange(new TopicExchange(letterExchange));
        return admin;
    }



    @Bean
    AmqpConnectionFactory connectionFactory(Environment environment) {
        return new SingleAmqpConnectionFactory(environment);
    }

    @Bean
    RabbitAmqpTemplate rabbitAmqpTemplate(AmqpConnectionFactory connectionFactory) {
        var template =  new RabbitAmqpTemplate(connectionFactory);
        template.setMessageConverter(new JacksonJsonMessageConverter());
        return template;
    }

    @Bean
    public Publisher<Letter> letterAmpqPublisher(RabbitAmqpTemplate rabbitTemplate) {
        return letter -> {
            rabbitTemplate.convertAndSend(letterExchange, letter.getReceiver(),
                    letter);
        };
    }
}
