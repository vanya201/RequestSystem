package org.friendship.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactionRabbitMQConfig
{
    public static final String REACTION_REQUEST_EXCHANGE = "reactionRequestExchange";
    public static final String REACTION_REQUEST_QUEUE = "reactionRequestQueue";
    public static final String REACTION_REQUEST_ROUTING_KEY = "reactionRequestRoute";

    @Bean
    public Queue reactionRequestQueue() {
        return new Queue(REACTION_REQUEST_QUEUE, true);
    }

    @Bean
    public TopicExchange reactionRequestExchange() {
        return new TopicExchange(REACTION_REQUEST_EXCHANGE);
    }


    @Bean
    public Binding reactionRequestBinding(Queue reactionRequestQueue, TopicExchange reactionRequestExchange) {
        return BindingBuilder.bind(reactionRequestQueue)
                .to(reactionRequestExchange)
                .with(REACTION_REQUEST_ROUTING_KEY);
    }

}
