package org.friendship.notify.impl.friend;

import lombok.RequiredArgsConstructor;
import org.friendship.config.rabbit.FriendRabbitMQConfig;
import org.friendship.notify.Notifiable;
import org.friendship.notify.RequestNotify;
import org.friendship.notify.annotation.FriendRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.friendship.notify.annotation.Notify;


@Component
@RequiredArgsConstructor
@Notify
public class FriendNotify implements Notifiable {

    private final RabbitTemplate rabbitTemplate;

    @FriendRequest(FriendRequest.Type.SEND)
    private void sendFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(
                FriendRabbitMQConfig.FRIEND_REQUEST_EXCHANGE,
                FriendRabbitMQConfig.FRIEND_REQUEST_ROUTING_KEY,
                friendRequest);
    }



    @FriendRequest(FriendRequest.Type.DECLINE)
    private void declineFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(
                FriendRabbitMQConfig.FRIEND_REQUEST_EXCHANGE,
                FriendRabbitMQConfig.DECLINE_FRIEND_REQUEST_ROUTING_KEY,
                friendRequest);
    }



    @FriendRequest(FriendRequest.Type.ACCEPT)
    private void acceptFriendRequest(RequestNotify friendRequest) {
        rabbitTemplate.convertAndSend(
                FriendRabbitMQConfig.FRIEND_REQUEST_EXCHANGE,
                FriendRabbitMQConfig.ACCEPT_FRIEND_REQUEST_ROUTING_KEY,
                friendRequest);
    }
}
