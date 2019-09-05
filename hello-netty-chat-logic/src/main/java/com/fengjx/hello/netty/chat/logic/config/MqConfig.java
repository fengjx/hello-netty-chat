package com.fengjx.hello.netty.chat.logic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengjianxin
 */
@Configuration
public class MqConfig {

    public static final String USER_INFO_QUEUE = "love-music-search-user-info-queue";

    @Bean
    public Queue userinfoQueue() {
        return new Queue(USER_INFO_QUEUE);
    }

    @Bean
    public TopicExchange userInfoExchange() {
        return new TopicExchange("user-info-exchange");
    }

    @Bean
    public Binding bindingUserInfo(Queue userinfoQueue, TopicExchange userInfoExchange) {
        return BindingBuilder.bind(userinfoQueue).to(userInfoExchange).with("#");
    }


}
