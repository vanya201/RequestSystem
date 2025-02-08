package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.models.FriendRequest;
import org.common.models.FriendRequestStatus;
import org.common.models.User;
import org.posterservice.dto.http.AcceptFriendRequestDTO;
import org.posterservice.dto.http.DeclineFriendRequestDTO;
import org.posterservice.dto.http.FriendRequestDTO;
import org.posterservice.event.AcceptFriendRequestEvent;
import org.posterservice.event.DeclineFriendRequestEvent;
import org.posterservice.event.FriendRequestEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.common.models.FriendRequestStatus.*;

@Component
@RequiredArgsConstructor
public class FriendShipService {
    private final UserSearchService userSearchService;
    private final FriendRequestService friendRequestService;
    private final ApplicationEventPublisher eventPublisher;

    private Map<FriendRequestStatus, String> statusMessages = Map.of(
            PENDING , "Friend Request Already Sent",
            ACCEPTED, "Friend Request Already Accepted",
            DECLINED, "Friend Request Was Declined"
    );

    @Transactional
    public void sendFriendRequest(User sender, FriendRequestDTO friendRequestDTO) {
        User receiver = userSearchService.searchUserByName(friendRequestDTO.getReceiver());

        if (friendRequestService.existsFriendRequest(sender, receiver)) {
            FriendRequest friendRequest = friendRequestService.getFriendRequest(sender, receiver);
            throw new RuntimeException(statusMessages.get(friendRequest.getStatus()));
        }

        if(sender.getFriends().contains(receiver))
            throw new RuntimeException("You are already friends with this user");

        friendRequestService.saveFriendRequest(FriendRequest.create(sender, receiver));
        eventPublisher.publishEvent(FriendRequestEvent.create(sender, receiver));
    }

    @Transactional
    public void acceptFriendRequest(User receiver, AcceptFriendRequestDTO friendRequestDTO) {
        User sender = userSearchService.searchUserByName(friendRequestDTO.getSender());

        FriendRequest friendRequest = friendRequestService.getFriendRequest(sender, receiver);
        if (friendRequest.isAccepted() || friendRequest.isDeclined())
            throw new RuntimeException(statusMessages.get(friendRequest.getStatus()));

        receiver.setFriend(sender);

        if(friendRequestService.existsFriendRequest(receiver, sender)) {
            FriendRequest friendRequest2 = friendRequestService.getFriendRequest(receiver, sender);
            friendRequest2.setStatus(ACCEPTED);
        }

        friendRequest.setStatus(ACCEPTED);
        friendRequestService.saveFriendRequest(friendRequest);
        eventPublisher.publishEvent(AcceptFriendRequestEvent.create(sender, receiver));
    }

    @Transactional
    public void declineFriendRequest(User receiver, DeclineFriendRequestDTO friendRequestDTO) {
        User sender = userSearchService.searchUserByName(friendRequestDTO.getSender());

        FriendRequest friendRequest = friendRequestService.getFriendRequest(sender, receiver);
        if (friendRequest.isDeclined() || friendRequest.isAccepted())
            throw new RuntimeException(statusMessages.get(friendRequest.getStatus()));

        friendRequest.setStatus(DECLINED);
        friendRequestService.saveFriendRequest(friendRequest);
        eventPublisher.publishEvent(DeclineFriendRequestEvent.create(sender, receiver));
    }
}
