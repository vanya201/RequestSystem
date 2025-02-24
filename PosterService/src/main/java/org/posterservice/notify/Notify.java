package org.posterservice.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class Notify implements Notifiable {

    private ConcurrentHashMap<Annotation, Method> methodCache;
    private final Set<Class<? extends Annotation>> registeredAnnotations = NotifyAnnotationRegister.getInstance().getAnnotations();

    private static final Logger logger = LoggerFactory.getLogger(Notify.class);



    public Notify() {
        initializeMethodsByAnnotations();
    }



    private void initializeMethodsByAnnotations() {
        methodCache = Arrays.stream(this.getClass().getDeclaredMethods())
                .flatMap(m -> Arrays.stream(m.getDeclaredAnnotations())
                        .filter(annotation -> registeredAnnotations.contains(annotation.annotationType()))
                        .map(annotation -> new AbstractMap.SimpleEntry<>(annotation, m)))
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue,
                        (existing, replacement) -> existing,
                        ConcurrentHashMap::new));
    }



    @Override
    public void notify(RequestNotify friendRequest) {
        Annotation annotationRequestNotify = registeredAnnotationTypeFirst(friendRequest.getClass().getDeclaredAnnotations());
        if (methodCache.containsKey(annotationRequestNotify)) {
            try {
                Method methodToInvoke = methodCache.get(annotationRequestNotify);
                methodToInvoke.setAccessible(true);
                methodToInvoke.invoke(this, friendRequest);
            } catch (Exception e) {
                logger.error("Error while invoking method", e);
            }
        }
    }



    private Annotation registeredAnnotationTypeFirst(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> registeredAnnotations.contains(annotation.annotationType()))
                .findAny()
                .orElseThrow();
    }

}
