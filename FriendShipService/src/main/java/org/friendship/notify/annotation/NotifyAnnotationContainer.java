package org.friendship.notify.annotation;

import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Set;

@Getter
public class NotifyAnnotationContainer
{

    private final Set<Class<? extends Annotation>> annotations;


    private NotifyAnnotationContainer() {
        annotations = Set.of (
                FriendRequest.class,
                ReactionRequest.class
                //maybe packet scan
        );
    }



    public static class NotifyAnnotationRegisterHolder {
        public static final NotifyAnnotationContainer INSTANCE = new NotifyAnnotationContainer();
    }


    public static NotifyAnnotationContainer getInstance() {
        return NotifyAnnotationRegisterHolder.INSTANCE;
    }

}
