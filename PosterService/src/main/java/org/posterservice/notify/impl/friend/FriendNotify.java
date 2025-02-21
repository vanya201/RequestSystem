package org.posterservice.notify.impl.friend;

import lombok.RequiredArgsConstructor;
import org.posterservice.config.rabbit.RabbitMQConfig;
import org.posterservice.notify.Notify;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.annotation.FriendRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendNotify extends Notify {

    private final RabbitTemplate rabbitTemplate;

    @FriendRequest(FriendRequest.Type.SEND)
    private void sendFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FRIEND_REQUEST_EXCHANGE, RabbitMQConfig.FRIEND_REQUEST_ROUTING_KEY, friendRequest);
    }



    @FriendRequest(FriendRequest.Type.DECLINE)
    private void declineFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FRIEND_REQUEST_EXCHANGE, RabbitMQConfig.DECLINE_FRIEND_REQUEST_ROUTING_KEY, friendRequest);
    }



    @FriendRequest(FriendRequest.Type.ACCEPT)
    private void acceptFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FRIEND_REQUEST_EXCHANGE, RabbitMQConfig.ACCEPT_FRIEND_REQUEST_ROUTING_KEY, friendRequest);
    }

}
