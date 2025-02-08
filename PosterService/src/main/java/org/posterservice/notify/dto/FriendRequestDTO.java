package org.posterservice.notify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRequestDTO {
    String sender;
    String recipient;
}
