package org.posterservice.event;

import org.common.model.User;
import org.posterservice.notify.impl.friend.dto.AcceptFriendRequestDTO;

import java.util.EventObject;

public class AcceptFriendRequestEvent extends EventObject {
    private AcceptFriendRequestEvent(Object source) {
        super(source);
    }

    public static AcceptFriendRequestEvent create(User sender, User receiver) {
        return new AcceptFriendRequestEvent(AcceptFriendRequestDTO.builder()
                .sender(sender.getUsername())
                .accepter(receiver.getUsername())
                .build());
    }
}
