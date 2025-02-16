package org.posterservice.notify.impl.friend.decorator;

import lombok.AllArgsConstructor;
import org.posterservice.notify.Notify;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.impl.friend.FriendNotify;
import org.posterservice.notify.impl.friend.FriendNotifyTelegram;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class FriendNotifyWithTelegram implements Notify {

    private final FriendNotify friendNotify;
    private final FriendNotifyTelegram friendNotifyTelegram;

    @Override
    public void notify(RequestNotify friendRequest) {
        friendNotify.notify(friendRequest);
        friendNotifyTelegram.notify(friendRequest);
    }
}
