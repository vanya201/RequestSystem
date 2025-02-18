package org.posterservice.notify.impl.friend;
import lombok.RequiredArgsConstructor;
import org.posterservice.config.rabbit.RabbitMQConfig;
import org.posterservice.notify.Notify;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.FriendRequestDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@RequiredArgsConstructor
public class FriendNotify implements Notify {

    private final RabbitTemplate rabbitTemplate;

    private final Map<String, String> routing = Map.of (
            AcceptFriendRequestDTO.class.getSimpleName(),   RabbitMQConfig.ACCEPT_FRIEND_REQUEST_ROUTING_KEY,
            DeclineFriendRequestDTO.class.getSimpleName(),  RabbitMQConfig.DECLINE_FRIEND_REQUEST_ROUTING_KEY,
            FriendRequestDTO.class.getSimpleName(),         RabbitMQConfig.FRIEND_REQUEST_ROUTING_KEY
    );


    @Override
    public void notify(RequestNotify friendRequest) throws IllegalArgumentException {
        var routingKey = routing.get(friendRequest.getClass().getSimpleName());
        if (routingKey == null)
            throw new IllegalArgumentException("Routing key not found");
        rabbitTemplate.convertAndSend(RabbitMQConfig.FRIEND_REQUEST_EXCHANGE, routingKey, friendRequest);
    }
}
