package org.posterservice.services;

import lombok.RequiredArgsConstructor;
import org.common.models.FriendRequest;
import org.common.models.FriendRequestStatus;
import org.common.models.User;
import org.posterservice.exception.FriendRequestNotFoundException;
import org.posterservice.repositories.FriendRequestRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;

    public FriendRequest getFriendRequest(User sender, User receiver) throws FriendRequestNotFoundException{
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(FriendRequestNotFoundException::new);
    }



    public boolean existsFriendRequest(User sender, User receiver) {
        return friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
    }



    public List<FriendRequest> getFriendRequestsForReceiver(User receiver, Pageable pageable) {
        return friendRequestRepository.findAllByReceiverAndStatus(receiver,
                FriendRequestStatus.PENDING, pageable).getContent();
    }



    public void createFriendRequest(User sender, User receiver) {
        friendRequestRepository.save(FriendRequest.create(sender, receiver));
    }



    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanUpFriendRequests() {
        friendRequestRepository.deleteByStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.deleteByStatus(FriendRequestStatus.DECLINED);
    }
}
