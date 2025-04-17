package org.friendship.repositories;

import org.common.model.FriendRequest;
import org.common.model.FriendRequestStatus;
import org.common.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
    void deleteByStatus(FriendRequestStatus status);
    Page<FriendRequest> findAllByReceiverAndStatus(User receiver, FriendRequestStatus status, Pageable pageable);

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
