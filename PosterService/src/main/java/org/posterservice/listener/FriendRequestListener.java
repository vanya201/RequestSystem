package org.posterservice.listener;

import lombok.RequiredArgsConstructor;
import org.posterservice.notify.Notify;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.friend.dto.FriendRequestDTO;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestListener {

    private final Notify friendNotify;

    @EventListener
    @Async
    public void friendRequestListen(FriendRequestEvent event) {
        // update cache for example
        var friendRequestDTO = (FriendRequestDTO)event.getSource();
        friendNotify.notify(friendRequestDTO);
    }



    @EventListener
    @Async
    public void acceptFriendRequestListen(AcceptFriendRequestEvent event) {
        var acceptFriendRequestDTO = (AcceptFriendRequestDTO)event.getSource();
        friendNotify.notify(acceptFriendRequestDTO);
    }



    @EventListener
    @Async
    public void declineFriendRequestListen(DeclineFriendRequestEvent event) {
        var declineFriendRequestDTO = (DeclineFriendRequestDTO)event.getSource();
        friendNotify.notify(declineFriendRequestDTO);
    }
}
