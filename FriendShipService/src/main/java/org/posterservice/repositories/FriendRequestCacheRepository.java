package org.posterservice.repositories;

import org.posterservice.model.FriendRequestCache;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@EnableRedisRepositories
public interface FriendRequestCacheRepository extends CrudRepository<FriendRequestCache, Long> {
    List<FriendRequestCache> findAllById(Set<Long> ids);
}
