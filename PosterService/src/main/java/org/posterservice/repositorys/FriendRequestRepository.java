package org.posterservice.repositorys;

import org.common.models.FriendRequest;
import org.common.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    Optional<FriendRequest> findBySenderAndReceiver(User sender, User receiver);
    Page<FriendRequest> findAllByReceiver(User receiver, Pageable pageable);

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
