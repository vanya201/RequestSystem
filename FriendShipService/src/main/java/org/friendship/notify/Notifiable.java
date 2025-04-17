package org.friendship.notify;

public interface Notifiable {
    default void notify(RequestNotify requestNotify) {}
}
