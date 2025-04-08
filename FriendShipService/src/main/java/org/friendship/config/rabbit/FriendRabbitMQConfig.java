package org.friendship.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FriendRabbitMQConfig {

    public static final String FRIEND_REQUEST_EXCHANGE = "friendRequestExchange";

    public static final String FRIEND_REQUEST_QUEUE = "sendFriendRequestQueue";
    public static final String ACCEPT_FRIEND_REQUEST_QUEUE = "acceptFriendRequestQueue";
    public static final String DECLINE_FRIEND_REQUEST_QUEUE = "declineFriendRequestQueue";

    public static final String FRIEND_REQUEST_ROUTING_KEY = "sendFriendRequestRoute";
    public static final String ACCEPT_FRIEND_REQUEST_ROUTING_KEY = "acceptFriendRequestRoute";
    public static final String DECLINE_FRIEND_REQUEST_ROUTING_KEY = "declineFriendRequestRoute";

    @Bean
    public Queue sendFriendRequestQueue() {
        return new Queue(FRIEND_REQUEST_QUEUE, true);
    }



    @Bean
    public Queue acceptFriendRequestQueue() {
        return new Queue(ACCEPT_FRIEND_REQUEST_QUEUE, true);
    }



    @Bean
    public Queue declineFriendRequestQueue() {
        return new Queue(DECLINE_FRIEND_REQUEST_QUEUE, true);
    }



    @Bean
    public TopicExchange friendRequestExchange() {
        return new TopicExchange(FRIEND_REQUEST_EXCHANGE);
    }



    @Bean
    public Binding friendRequestBinding(Queue sendFriendRequestQueue, TopicExchange friendRequestExchange) {
        return BindingBuilder.bind(sendFriendRequestQueue)
                .to(friendRequestExchange)
                .with(FRIEND_REQUEST_ROUTING_KEY);
    }



    @Bean
    public Binding acceptFriendRequestBinding(Queue acceptFriendRequestQueue, TopicExchange friendRequestExchange) {
        return BindingBuilder.bind(acceptFriendRequestQueue)
                .to(friendRequestExchange)
                .with(ACCEPT_FRIEND_REQUEST_ROUTING_KEY);
    }



    @Bean
    public Binding declineFriendRequestBinding(Queue declineFriendRequestQueue, TopicExchange friendRequestExchange) {
        return BindingBuilder.bind(declineFriendRequestQueue)
                .to(friendRequestExchange)
                .with(DECLINE_FRIEND_REQUEST_ROUTING_KEY);
    }



    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }



    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(converter);
        return template;
    }
}
