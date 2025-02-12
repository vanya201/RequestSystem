package org.posterservice.notify.impl.dto;

import lombok.Builder;
import lombok.Data;
import org.posterservice.notify.FriendRequestNotify;

@Data
@Builder
public class AcceptFriendRequestDTO implements FriendRequestNotify {
    String sender;
    String accepter;
}
