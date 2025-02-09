package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.models.FriendRequest;
import org.common.models.FriendRequestStatus;
import org.common.models.User;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.posterservice.exception.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.common.models.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final UserSearchService userSearchService;
    private final FriendRequestService friendRequestService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void sendFriendRequest(User sender, String receiverName) {
        User receiver = userSearchService.searchUserByName(receiverName);

        if(sender.getFriends().contains(receiver))
            throw new AlreadyFriendsException();

        if (friendRequestService.existsFriendRequest(receiver, sender))
            throw new FriendRequestAlreadySentForYou();

        if (friendRequestService.existsFriendRequest(sender, receiver)) {
            var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

            if(friendRequest.isPending())
                throw new FriendRequestAlreadySentException();

            if(friendRequest.isAccepted())
                throw new FriendRequestAlreadyAcceptException();

            if(friendRequest.isDeclined())
                throw new FriendRequestDeclinedException();
        }

        friendRequestService.saveFriendRequest(FriendRequest.create(sender, receiver));
        eventPublisher.publishEvent(FriendRequestEvent.create(sender, receiver));
    }

    @Transactional
    public void acceptFriendRequest(User receiver, String senderName) {
        User sender = userSearchService.searchUserByName(senderName);

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(ACCEPTED);
        receiver.setFriend(sender);

        friendRequestService.saveFriendRequest(friendRequest);
        eventPublisher.publishEvent(AcceptFriendRequestEvent.create(sender, receiver));
    }

    @Transactional
    public void declineFriendRequest(User receiver, String senderName) {
        User sender = userSearchService.searchUserByName(senderName);

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(DECLINED);

        friendRequestService.saveFriendRequest(friendRequest);
        eventPublisher.publishEvent(DeclineFriendRequestEvent.create(sender, receiver));
    }
}
