package org.posterservice.repositories;

import org.common.models.FriendRequest;
import org.common.models.FriendRequestStatus;
import org.common.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
    Page<FriendRequest> findAllByReceiver(User receiver, Pageable pageable);
    void deleteByStatus(FriendRequestStatus status);

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
