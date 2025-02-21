package org.posterservice.event;
import org.common.models.User;
import org.posterservice.notify.impl.friend.dto.SendFriendRequestDTO;

import java.util.EventObject;

public class SendFriendRequestEvent extends EventObject {
    private SendFriendRequestEvent(Object source) {
        super(source);
    }

    public static SendFriendRequestEvent create(User sender, User receiver) {
        return new SendFriendRequestEvent(SendFriendRequestDTO.builder()
                .sender(sender.getUsername())
                .recipient(receiver.getUsername())
                .build());
    }
}
