package org.friendship.listener;

import lombok.RequiredArgsConstructor;
import org.friendship.event.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class TransactionFriendRequestListener {

    private final ApplicationEventPublisher eventPublisher;


    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendFriendRequestListen(SendFriendRequestEvent event) {
        eventPublisher.publishEvent(new AddFriendRequestCacheEvent(event.getSource()));
        eventPublisher.publishEvent(new SendFriendRequestNotifyEvent(event.getSource()));
    }



    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void acceptFriendRequestListen(AcceptFriendRequestEvent event) {
        eventPublisher.publishEvent(new DelFriendRequestCacheEvent(event.getSource()));
        eventPublisher.publishEvent(new AcceptFriendRequestNotifyEvent(event.getSource()));
    }



    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void declineFriendRequestListen(DeclineFriendRequestEvent event) {
        eventPublisher.publishEvent(new DelFriendRequestCacheEvent(event.getSource()));
        eventPublisher.publishEvent(new DeclineFriendRequestNotifyEvent(event.getSource()));
    }
}
