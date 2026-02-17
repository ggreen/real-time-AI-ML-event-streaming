/*
 *
 *  * Copyright 2023 VMware, Inc.
 *  * SPDX-License-Identifier: GPL-3.0
 *
 */

package realtime.ai.ml.event.streaming.web;

import lombok.extern.slf4j.Slf4j;
import nyla.solutions.core.patterns.integration.Publisher;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbitmq.client.RabbitAmqpTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;


    @Value("${spring.rabbitmq.username:guest}")
    private String username;

    @Value("${spring.rabbitmq.password:guest}")
    private String password;

    @Value("${spring.rabbitmq.host:127.0.0.1}")
    private String hostname;

    @Value("${letter.exchange:letters}")
    private String letterExchange;

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter messageConverter()
    {
        return new JacksonJsonMessageConverter();
    }


    @Profile("amqp")
    @Bean
    public Publisher<Letter> letterPublisher(RabbitTemplate rabbitTemplate)
    {
        rabbitTemplate.setExchange(letterExchange);
        return rabbitTemplate::convertAndSend;
    }

    @Profile("amqp1")
    @Bean
    public Publisher<Letter> letterAmpqPublisher(RabbitAmqpTemplate rabbitTemplate)
    {
        rabbitTemplate.setExchange(letterExchange);
        return rabbitTemplate::convertAndSend;
    }

}
