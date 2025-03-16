package org.posterservice.event;

import java.util.EventObject;

public class SendFriendRequestEvent extends EventObject {
    public SendFriendRequestEvent(Object source) {
        super(source);
    }
}
