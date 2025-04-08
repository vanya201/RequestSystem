package org.friendship.event;

import java.util.EventObject;

public class AddFriendRequestCacheEvent extends EventObject {
    public AddFriendRequestCacheEvent(Object source) {
        super(source);
    }
}
