package org.posterservice.notify.dto.impl;

import lombok.Builder;
import lombok.Data;
import org.posterservice.notify.dto.FriendRequestNotify;

@Data
@Builder
public class AcceptFriendRequestDTO implements FriendRequestNotify {
    String sender;
    String accepter;
}
