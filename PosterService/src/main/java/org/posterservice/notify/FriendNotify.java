package org.posterservice.notify;

import org.posterservice.notify.dto.FriendRequestNotify;

public interface FriendNotify {
    void notify(FriendRequestNotify friendRequest);
}
