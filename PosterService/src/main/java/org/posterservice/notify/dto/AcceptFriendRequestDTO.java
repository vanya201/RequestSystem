package org.posterservice.notify.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptFriendRequestDTO {
    String sender;
    String accepter;
}
