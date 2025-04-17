package org.friendship.listener;

import lombok.RequiredArgsConstructor;
import org.friendship.config.application.FriendShipMapper;
import org.friendship.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestCacheListener {


    private final FriendShipMapper friendShipMapper;

    @Async
    @EventListener
    public void addFriendRequestListen(AddFriendRequestCacheEvent event) {


    }



    @Async
    @EventListener
    public void delFriendRequestListen(DelFriendRequestCacheEvent event) {

    }
}
