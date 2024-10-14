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
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import realtime.ai.ml.event.streaming.web.domain.Letter;

@Configuration
@Slf4j
public class RabbitConfig {

    @Value("${spring.application.name}")
    private String applicationName;


    @Value("${spring.rabbitmq.username:guest}")
    private String username = "guest";

    @Value("${spring.rabbitmq.password:guest}")
    private String password   = "guest";

    @Value("${spring.rabbitmq.host:127.0.0.1}")
    private String hostname = "localhost";

    @Value("${letter.exchange:letters}")
    private String letterExchange;

    @Bean
    ConnectionNameStrategy connectionNameStrategy(){
        return (connectionFactory) -> applicationName;
    }

    @Bean
    public MessageConverter messageConverter()
    {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public Publisher<Letter> letterPublisher(RabbitTemplate rabbitTemplate)
    {
        rabbitTemplate.setExchange(letterExchange);
        return letter -> rabbitTemplate.convertAndSend(letter);
    }

}
