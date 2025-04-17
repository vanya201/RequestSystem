package org.friendship.notify.impl.friend.dto;

import lombok.Builder;
import lombok.Data;
import org.friendship.notify.RequestNotify;
import org.friendship.notify.annotation.FriendRequest;

@Data
@Builder
@FriendRequest(FriendRequest.Type.DECLINE)
public class DeclineFriendRequestDTO implements RequestNotify {
    String requester;
    String decliner;
}
