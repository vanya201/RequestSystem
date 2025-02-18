package org.notifyservice.listener;

import lombok.RequiredArgsConstructor;
import org.posterservice.config.rabbit.RabbitMQConfig;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.FriendRequestDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.SimpleBrokerMessageHandler;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class FriendRequestListener {
    private final SimpMessagingTemplate messagingTemplate;


    @RabbitListener(queues = RabbitMQConfig.FRIEND_REQUEST_QUEUE)
    public void receiveMessage(FriendRequestDTO friendRequestDTO) {

        messagingTemplate.convertAndSendToUser(
                friendRequestDTO.getRecipient(),
                "/queue/request",
                friendRequestDTO.getSender());
    }


    @RabbitListener(queues = RabbitMQConfig.ACCEPT_FRIEND_REQUEST_QUEUE)
    public void receiveMessage(AcceptFriendRequestDTO acceptFriendRequestDTO) {

        messagingTemplate.convertAndSendToUser(
                acceptFriendRequestDTO.getSender(),
                "/queue/accept",
                acceptFriendRequestDTO.getAccepter());
    }


    @RabbitListener(queues = RabbitMQConfig.DECLINE_FRIEND_REQUEST_QUEUE)
    public void receiveMessage(DeclineFriendRequestDTO declineFriendRequestDTO) {

        messagingTemplate.convertAndSendToUser(
                declineFriendRequestDTO.getRequester(),
                "/queue/decline",
                declineFriendRequestDTO.getDecliner());
    }
}
