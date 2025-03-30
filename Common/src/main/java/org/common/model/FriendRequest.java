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
        name = "friend_request",
        indexes = {
                @Index(name = "idx_friend_request_sender_receiver", columnList = "sender_id, receiver_id")
        }
)
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private FriendRequestStatus status = FriendRequestStatus.PENDING;


    @Version
    private Long version;



    static public FriendRequest create(User sender, User receiver) {
        return FriendRequest.builder()
                .sender(sender)
                .receiver(receiver)
                .build();
    }

    public boolean isAccepted() {
        return status == FriendRequestStatus.ACCEPTED;
    }

    public boolean isPending() {
        return status == FriendRequestStatus.PENDING;
    }

    public boolean isDeclined() {
        return status == FriendRequestStatus.DECLINED;
    }

}