package org.friendship.listener;

import lombok.RequiredArgsConstructor;
import org.common.model.FriendRequest;
import org.friendship.config.application.FriendShipMapper;
import org.friendship.event.*;
import org.friendship.notify.Notifiable;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestNotifyListener {

    private final Notifiable friendNotify;
    private final FriendShipMapper friendShipMapper;

    @Async
    @EventListener
    public void sendFriendRequestListen(SendFriendRequestEvent event) {
        var friendRequest = (FriendRequest) event.getSource();
        var sendFriendRequestDTO = friendShipMapper.toSendFriendRequestDTO(friendRequest);
        friendNotify.notify(sendFriendRequestDTO);
    }



    @Async
    @EventListener
    public void acceptFriendRequestListen(AcceptFriendRequestNotifyEvent event) {
        var friendRequest = (FriendRequest) event.getSource();
        var acceptFriendRequestDTO = friendShipMapper.toAcceptFriendRequestDTO(friendRequest);
        friendNotify.notify(acceptFriendRequestDTO);
    }



    @Async
    @EventListener
    public void declineFriendRequestListen(DeclineFriendRequestNotifyEvent event) {
        var friendRequest = (FriendRequest) event.getSource();
        var declineFriendRequestDTO = friendShipMapper.toDeclineFriendRequestDTO(friendRequest);
        friendNotify.notify(declineFriendRequestDTO);
    }
}
