package org.friendship.services;

import lombok.RequiredArgsConstructor;
import org.common.model.User;
import org.friendship.event.AcceptFriendRequestEvent;
import org.friendship.event.DeclineFriendRequestEvent;
import org.friendship.event.SendFriendRequestEvent;
import org.friendship.exception.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.common.model.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendsService friendsService;
    private final FriendRequestService friendRequestService;
    private final SearchUsersService searchUsersService;

    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public void sendFriendRequest(UserDetails senderDetails, String receiverName) {
        User receiver = searchUsersService.searchUserByName(receiverName);
        User sender =  searchUsersService.searchUserByName(senderDetails.getUsername());

         if(friendsService.existsFriends(sender, receiver))
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
        var friendRequest = friendRequestService.createFriendRequest(sender, receiver);
        eventPublisher.publishEvent(new SendFriendRequestEvent(friendRequest));
    }



    @Transactional
    public void acceptFriendRequest(UserDetails receiverDetails, String senderName) {
        User sender = searchUsersService.searchUserByName(senderName);
        User receiver =  searchUsersService.searchUserByName(receiverDetails.getUsername());

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(ACCEPTED); //optimistic lock
        friendsService.addFriend(receiver, sender);

        eventPublisher.publishEvent(new AcceptFriendRequestEvent(friendRequest));
    }



    @Transactional
    public void declineFriendRequest(UserDetails receiverDetails, String senderName) {
        User sender = searchUsersService.searchUserByName(senderName);
        User receiver = searchUsersService.searchUserByName(receiverDetails.getUsername());

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(DECLINED); //optimistic lock

        eventPublisher.publishEvent(new DeclineFriendRequestEvent(friendRequest));
    }
}
