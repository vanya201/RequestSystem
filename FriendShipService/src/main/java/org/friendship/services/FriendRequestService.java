package org.friendship.services;

import lombok.RequiredArgsConstructor;
import org.common.model.FriendRequest;
import org.common.model.FriendRequestStatus;
import org.common.model.User;
import org.friendship.config.application.FriendShipMapper;
import org.friendship.dto.http.FriendRequestDTO;
import org.friendship.exception.FriendRequestNotFoundException;
import org.friendship.repositories.FriendRequestRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final FriendShipMapper friendShipMapper;

    public FriendRequest getFriendRequest(User sender, User receiver) throws FriendRequestNotFoundException{
        return friendRequestRepository.findBySenderAndReceiver(sender, receiver)
                .orElseThrow(FriendRequestNotFoundException::new);
    }



    public boolean existsFriendRequest(User sender, User receiver) {
        return friendRequestRepository.existsBySenderAndReceiver(sender, receiver);
    }


    //@CacheFriendRequestsForUser(receiver, pageable)
    public List<FriendRequestDTO> getFriendRequestsByUser(User user, Pageable pageable) {
        List<FriendRequest> friendRequests = friendRequestRepository.findAllByReceiverAndStatus(user,
                FriendRequestStatus.PENDING, pageable).getContent();
        return friendShipMapper.toFriendRequestDTOList(friendRequests);
    }



    public FriendRequest createFriendRequest(User sender, User receiver) {
        return friendRequestRepository.save(FriendRequest.create(sender, receiver));
    }



    @Transactional
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void cleanUpFriendRequests() {
        friendRequestRepository.deleteByStatus(FriendRequestStatus.ACCEPTED);
        friendRequestRepository.deleteByStatus(FriendRequestStatus.DECLINED);
    }
}
