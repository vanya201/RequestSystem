package org.posterservice.event;
import org.common.models.User;
import org.posterservice.notify.dto.FriendRequestDTO;

import java.util.EventObject;

public class FriendRequestEvent extends EventObject {
    private FriendRequestEvent(Object source) {
        super(source);
    }

    public static FriendRequestEvent create(User sender, User receiver) {
        return new FriendRequestEvent(FriendRequestDTO.builder()
                .sender(sender.getUsername())
                .recipient(receiver.getUsername())
                .build());
    }
}
