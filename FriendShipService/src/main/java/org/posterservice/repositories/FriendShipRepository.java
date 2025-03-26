package org.posterservice.repositories;

import org.common.model.FriendShip;
import org.common.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendShip, Long> {
    void deleteByUser1AndUser2(User user1, User user2);
    boolean existsByUser1AndUser2(User user1, User user2);
}

