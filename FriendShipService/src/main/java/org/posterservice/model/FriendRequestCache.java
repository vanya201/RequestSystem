package org.posterservice.model;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.annotation.Id;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@RedisHash("friend_request")
@Builder
public class FriendRequestCache {
    @Id
    private Long id;

    private String receiver;

    private String sender;
}
