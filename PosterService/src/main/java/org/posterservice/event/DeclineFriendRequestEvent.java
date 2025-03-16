package org.posterservice.event;

import java.util.EventObject;

public class DeclineFriendRequestEvent extends EventObject {
    public DeclineFriendRequestEvent(Object source) {
        super(source);
    }
}
