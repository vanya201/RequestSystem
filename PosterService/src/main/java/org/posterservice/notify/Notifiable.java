package org.posterservice.notify;

public interface Notifiable {
    default void notify(RequestNotify requestNotify) {}
}
