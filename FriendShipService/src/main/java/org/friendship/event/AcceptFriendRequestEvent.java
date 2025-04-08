package org.friendship.event;

import java.util.EventObject;

public class AcceptFriendRequestEvent extends EventObject {
    public AcceptFriendRequestEvent(Object source) {
        super(source);
    }
}
