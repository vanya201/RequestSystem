package org.posterservice.notify.impl;
import lombok.RequiredArgsConstructor;
import org.posterservice.config.rabbit.RabbitMQConfig;
import org.posterservice.notify.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.dto.FriendRequestDTO;
import org.posterservice.notify.FriendNotify;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class FriendNotifyRabbit implements FriendNotify {

    private final RabbitTemplate rabbitTemplate;

    private Map<String, String> routing = Map.of(
            AcceptFriendRequestDTO.class.getSimpleName(), RabbitMQConfig.ACCEPT_FRIEND_REQUEST_ROUTING_KEY,
            DeclineFriendRequestDTO.class.getSimpleName(), RabbitMQConfig.DECLINE_FRIEND_REQUEST_ROUTING_KEY,
            FriendRequestDTO.class.getSimpleName(), RabbitMQConfig.FRIEND_REQUEST_ROUTING_KEY
    );

    @Override
    public void notify(Object friendRequest) {
        String routingKey = routing.get(friendRequest.getClass().getSimpleName());
        rabbitTemplate.convertAndSend(RabbitMQConfig.FRIEND_REQUEST_EXCHANGE, routingKey, friendRequest);
    }
}
