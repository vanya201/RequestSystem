package org.common.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "friend_ship",
        indexes = {
                @Index(name = "idx_friend_ship_user1_user2", columnList = "user1_id, user2_id", unique = true)
        }
)
public class FriendShip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    static public FriendShip create(User user, User friend) {
        return FriendShip.builder()
                .user1(user)
                .user2(friend)
                .build();
    }
}
