package org.posterservice.listener;

import lombok.RequiredArgsConstructor;
import org.common.model.FriendRequest;
import org.common.model.FriendShip;
import org.posterservice.cache.manager.FriendRequestForReceiverCacheService;
import org.posterservice.config.application.FriendShipMapper;
import org.posterservice.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestCacheListener {

    private final FriendRequestForReceiverCacheService friendRequestForReceiverCacheService;
    private final FriendShipMapper friendShipMapper;

    @Async
    @EventListener
    public void addFriendRequestListen(AddFriendRequestCacheEvent event) {
        var friendRequest = friendShipMapper.toFriendRequestCache((FriendRequest) event.getSource());
        //friendRequestForReceiverCacheService.saveFriendRequest(friendRequest);
    }



    @Async
    @EventListener
    public void delFriendRequestListen(DelFriendRequestCacheEvent event) {
        var friendRequest = friendShipMapper.toFriendRequestCache((FriendRequest) event.getSource());
        //friendRequestForReceiverCacheService.deleteFriendRequest(friendRequest);
    }
}
