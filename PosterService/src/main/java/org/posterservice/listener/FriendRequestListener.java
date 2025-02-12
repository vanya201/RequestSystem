package org.posterservice.listener;

import lombok.RequiredArgsConstructor;
import org.posterservice.notify.FriendNotify;
import org.posterservice.notify.impl.dto.AcceptFriendRequestDTO;
import org.posterservice.notify.impl.dto.DeclineFriendRequestDTO;
import org.posterservice.notify.impl.dto.FriendRequestDTO;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendRequestListener {

    private final FriendNotify friendNotifyRabbit;

    @EventListener
    @Async
    public void friendRequestListen(FriendRequestEvent event) {
        // update cache for example
        var friendRequestDTO = (FriendRequestDTO)event.getSource();
        friendNotifyRabbit.notify(friendRequestDTO);
    }



    @EventListener
    @Async
    public void acceptFriendRequestListen(AcceptFriendRequestEvent event) {
        var acceptFriendRequestDTO = (AcceptFriendRequestDTO)event.getSource();
        friendNotifyRabbit.notify(acceptFriendRequestDTO);
    }



    @EventListener
    @Async
    public void declineFriendRequestListen(DeclineFriendRequestEvent event) {
        var declineFriendRequestDTO = (DeclineFriendRequestDTO)event.getSource();
        friendNotifyRabbit.notify(declineFriendRequestDTO);
    }
}
