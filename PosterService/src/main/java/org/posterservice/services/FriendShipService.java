package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.model.User;
import org.common.user.details.UserDetailsImpl;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.SendFriendRequestEvent;
import org.posterservice.exception.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.common.model.FriendRequestStatus.*;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendsForUserService friendsForUserService;
    private final FriendRequestService friendRequestService;
    private final SearchUsersService searchUsersService;

    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public void sendFriendRequest(UserDetails senderDetails, String receiverName) {
        User receiver = searchUsersService.searchUserByName(receiverName);
        User sender =  searchUsersService.searchUserByName(senderDetails.getUsername());

         if(friendsForUserService.existsFriends(sender, receiver))
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
        friendsForUserService.addFriend(receiver, sender);

        eventPublisher.publishEvent(new AcceptFriendRequestEvent(friendRequest));
    }



    @Transactional
    public void declineFriendRequest(UserDetails receiverDetails, String senderName) {
        User sender = searchUsersService.searchUserByName(senderName);
        User receiver = ((UserDetailsImpl) receiverDetails).getUser();

        var friendRequest = friendRequestService.getFriendRequest(sender, receiver);

        if (friendRequest.isAccepted())
            throw new FriendRequestAlreadyAcceptException();
        if(friendRequest.isDeclined())
            throw new FriendRequestDeclinedException();

        friendRequest.setStatus(DECLINED); //optimistic lock

        eventPublisher.publishEvent(new DeclineFriendRequestEvent(friendRequest));
    }
}
