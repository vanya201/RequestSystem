package org.posterservice.notify.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface FriendRequest {
    Type value();

    enum Type {
        SEND, ACCEPT, DECLINE
    }
}
