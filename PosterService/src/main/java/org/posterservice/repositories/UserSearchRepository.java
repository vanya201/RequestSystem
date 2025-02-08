package org.posterservice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.common.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserSearchRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    Page<User> findByUsernameLike(String firstname, Pageable pageable);
    Page<User> findByFriendsContains(User user, Pageable pageable);

    @Query(value = "SELECT * FROM users u WHERE u.username % :username", nativeQuery = true)
    Page<User> findSimilarUsernames(@Param("username") String username, Pageable pageable);
}
