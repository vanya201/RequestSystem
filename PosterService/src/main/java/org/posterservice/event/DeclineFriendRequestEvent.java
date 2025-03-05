package org.posterservice.event;

import org.common.model.User;
import org.posterservice.notify.impl.friend.dto.DeclineFriendRequestDTO;

import java.util.EventObject;

public class DeclineFriendRequestEvent extends EventObject {
    public DeclineFriendRequestEvent(Object source) {
        super(source);
    }

    public static DeclineFriendRequestEvent create(User sender, User receiver) {
        return new DeclineFriendRequestEvent(DeclineFriendRequestDTO.builder()
                .requester(sender.getUsername())
                .decliner(receiver.getUsername())
                .build());
    }
}
