package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.models.FriendRequest;
import org.common.models.User;
import org.posterservice.repositorys.FriendRequestRepository;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequest getFriendRequest(User sender, User receiver) {
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
    }

    public boolean existsFriendRequest(User sender, User receiver) {
        return friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
    }
    public List<FriendRequest> getFriendRequestsForReceiver(User receiver, Pageable pageable) {
        return friendRequestRepository.findAllByReceiver(receiver, pageable).getContent();
    }

    public void saveFriendRequest(FriendRequest friendRequest) {
        friendRequestRepository.save(friendRequest);
    }
}
