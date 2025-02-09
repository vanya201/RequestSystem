package org.posterservice.notify.dto.impl;

import lombok.Builder;
import lombok.Data;
import org.posterservice.notify.dto.FriendRequestNotify;

@Data
@Builder
public class FriendRequestDTO implements FriendRequestNotify {
    String sender;
    String recipient;
}
