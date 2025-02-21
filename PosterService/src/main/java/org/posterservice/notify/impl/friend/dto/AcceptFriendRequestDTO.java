package org.posterservice.notify.impl.friend.dto;

import lombok.Builder;
import lombok.Data;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.annotation.FriendRequest;

@Data
@Builder
@FriendRequest(FriendRequest.Type.ACCEPT)
public class AcceptFriendRequestDTO implements RequestNotify {
    String sender;
    String accepter;
}
