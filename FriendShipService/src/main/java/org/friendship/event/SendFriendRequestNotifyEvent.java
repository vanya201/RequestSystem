package org.friendship.event;

import java.util.EventObject;

public class SendFriendRequestNotifyEvent extends EventObject {
    public SendFriendRequestNotifyEvent(Object source) {
        super(source);
    }
}
