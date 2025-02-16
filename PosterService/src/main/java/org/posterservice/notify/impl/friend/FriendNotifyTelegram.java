package org.posterservice.notify.impl.friend;

import lombok.RequiredArgsConstructor;
import org.posterservice.notify.Notify;
import org.posterservice.notify.RequestNotify;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FriendNotifyTelegram implements Notify {


    @Override
    public void notify(RequestNotify friendRequest) {
        //TODO
    }
}
