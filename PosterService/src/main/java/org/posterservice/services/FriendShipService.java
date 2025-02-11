package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.models.FriendRequest;
import org.common.models.User;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.posterservice.exception.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.common.models.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final UserFriendsService userFriendService;
    private final FriendRequestService friendRequestService;
    private final SearchUsersService searchUsersService;

    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void sendFriendRequest(User senderDetails, String receiverName) {
        User receiver = searchUsersService.searchUserByName(receiverName);
        User sender =  searchUsersService.searchUserByName(senderDetails.getUsername());

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

        //sender and receiver is one unique key
        friendRequestService.saveFriendRequest(FriendRequest.create(sender, receiver));
        eventPublisher.publishEvent(FriendRequestEvent.create(sender, receiver));
    }



    @Transactional
    public void acceptFriendRequest(User receiverDetails, String senderName) {
        User sender = searchUsersService.searchUserByName(senderName);
        User receiver =  searchUsersService.searchUserByName(receiverDetails.getUsername());

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(ACCEPTED); //optimistic lock
        userFriendService.addFriend(receiver, sender);

        eventPublisher.publishEvent(AcceptFriendRequestEvent.create(sender, receiver));
    }



    @Transactional
    public void declineFriendRequest(User receiver, String senderName) {
        User sender = searchUsersService.searchUserByName(senderName);

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(DECLINED); //optimistic lock

        eventPublisher.publishEvent(DeclineFriendRequestEvent.create(sender, receiver));
    }
}
