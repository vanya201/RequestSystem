package org.posterservice.cache.manager;

import lombok.RequiredArgsConstructor;
import org.posterservice.model.FriendRequestCache;
import org.posterservice.repositories.FriendRequestCacheRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FriendRequestForReceiverCacheService {
    private final RedisTemplate<String, Long> friendRequestsForUserRedisTemplate;
    private final FriendRequestCacheRepository friendRequestCacheRepository;


    public void saveFriendRequest(FriendRequestCache request) {
        request = friendRequestCacheRepository.save(request);
        friendRequestsForUserRedisTemplate.opsForZSet().add(request.getReceiver(), request.getId(), System.currentTimeMillis());
    }



    public List<FriendRequestCache> findFriendRequests(String receiver, Pageable pageable) {
        ZSetOperations<String, Long> zSetOps = friendRequestsForUserRedisTemplate.opsForZSet();
        Set<Long> ids = Optional.of(zSetOps.reverseRange(receiver, pageable.getOffset(), pageable.getOffset() + pageable.getPageSize() - 1))
                .orElseThrow(() -> new RuntimeException("Cache not found"));

        return ids.stream()
                .map(id -> friendRequestCacheRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Cache not found"))
                )
                .collect(Collectors.toList());
    }



    public void deleteFriendRequest(FriendRequestCache request) {
        friendRequestsForUserRedisTemplate.opsForZSet().remove(request.getReceiver(), request.getId());
        friendRequestCacheRepository.deleteById(request.getId());
    }
}
