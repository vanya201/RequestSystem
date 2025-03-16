package org.posterservice.listener;

import lombok.RequiredArgsConstructor;
import org.common.model.FriendRequest;
import org.posterservice.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestCacheListener {

    @Async
    @EventListener
    public void addFriendRequestListen(AddFriendRequestCacheEvent event) {
        var friendRequest = (FriendRequest) event.getSource();
        //TODO
    }



    @Async
    @EventListener
    public void delFriendRequestListen(DelFriendRequestCacheEvent event) {
        var friendRequest = (FriendRequest) event.getSource();
        //TODO
    }
}
