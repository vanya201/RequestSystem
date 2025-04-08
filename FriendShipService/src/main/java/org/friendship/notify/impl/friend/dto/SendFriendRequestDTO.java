package org.friendship.notify.impl.friend.dto;

import lombok.Builder;
import lombok.Data;
import org.friendship.notify.RequestNotify;
import org.friendship.notify.annotation.FriendRequest;

@Data
@Builder
@FriendRequest(FriendRequest.Type.SEND)
public class SendFriendRequestDTO implements RequestNotify {
    String sender;
    String recipient;
}
