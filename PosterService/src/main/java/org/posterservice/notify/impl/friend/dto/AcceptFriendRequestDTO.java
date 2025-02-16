package org.posterservice.notify.impl.friend.dto;

import lombok.Builder;
import lombok.Data;
import org.posterservice.notify.RequestNotify;

@Data
@Builder
public class AcceptFriendRequestDTO implements RequestNotify {
    String sender;
    String accepter;
}
