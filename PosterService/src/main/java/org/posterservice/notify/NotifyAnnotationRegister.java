package org.posterservice.notify;

import lombok.Getter;
import org.posterservice.notify.annotation.FriendRequest;
import org.posterservice.notify.annotation.ReactionRequest;

import java.lang.annotation.Annotation;
import java.util.Set;

@Getter
public class NotifyAnnotationRegister {

    private final Set<Class<? extends Annotation>> annotations;



    private NotifyAnnotationRegister() {
        annotations = Set.of (
                FriendRequest.class,
                ReactionRequest.class
        );
    }



    public static class NotifyAnnotationRegisterHolder {
        public static final NotifyAnnotationRegister INSTANCE = new NotifyAnnotationRegister();
    }



    public static NotifyAnnotationRegister getInstance() {
        return NotifyAnnotationRegisterHolder.INSTANCE;
    }

}
