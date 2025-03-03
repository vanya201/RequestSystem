package org.posterservice.notify.impl.reaction;
import lombok.RequiredArgsConstructor;
import org.posterservice.notify.Notifiable;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.annotation.Notify;
import org.posterservice.notify.annotation.ReactionRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static org.posterservice.config.rabbit.ReactionRabbitMQConfig.REACTION_REQUEST_EXCHANGE;
import static org.posterservice.config.rabbit.ReactionRabbitMQConfig.REACTION_REQUEST_ROUTING_KEY;

@Component
@RequiredArgsConstructor
@Notify
public class ReactionNotify implements Notifiable {
    private final RabbitTemplate rabbitTemplate;

    @ReactionRequest
    private void sendReactionRequest(RequestNotify requestNotify) {
        rabbitTemplate.convertAndSend(
                REACTION_REQUEST_EXCHANGE ,
                REACTION_REQUEST_ROUTING_KEY,
                requestNotify);
    }
}
