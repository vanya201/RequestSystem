package org.posterservice.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Notify {

    private final ConcurrentHashMap<Annotation, Method> methodCache = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(Notify.class);


    public void notify(RequestNotify friendRequest) {
        Annotation annotationRequestNotify = registeredAnnotationTypeFirst(friendRequest.getClass().getDeclaredAnnotations());
        Method methodToInvoke = methodCache.get(annotationRequestNotify);
        if (methodToInvoke == null) {
            methodToInvoke = getMethodByAnnotation(annotationRequestNotify);
            methodCache.put(annotationRequestNotify, methodToInvoke);
        }
        try {
            methodToInvoke.setAccessible(true);
            methodToInvoke.invoke(this, friendRequest);
        } catch (Exception e) {
            logger.error("Error invoking method", e);
        }
    }



    private Annotation registeredAnnotationTypeFirst(Annotation[] annotations) {
        Set<Class<? extends Annotation>> registeredAnnotations = NotifyAnnotationRegister.getInstance().getAnnotations();
        return Arrays.stream(annotations).filter(annotation ->
                registeredAnnotations.contains(annotation.annotationType())
        ).findFirst().orElseThrow();
    }



    private Method getMethodByAnnotation(Annotation annotationRequestNotify) {
        return Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(annotationRequestNotify.annotationType()))
                .filter(m -> m.getAnnotation(annotationRequestNotify.annotationType()).equals(annotationRequestNotify))
                .findFirst()
                .orElseThrow();
    }


}
