package org.posterservice.notify.proxy;

import org.posterservice.notify.Notifiable;
import org.posterservice.notify.RequestNotify;
import org.posterservice.notify.annotation.NotifyAnnotationContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class NotifyProxy implements InvocationHandler {

    private final Object target;
    private ConcurrentHashMap<Annotation, Method> methodCache;
    private final Set<Class<? extends Annotation>> registeredAnnotations
            = NotifyAnnotationContainer.getInstance().getAnnotations();

    public NotifyProxy(Object target) {
        this.target = target;
        initializeMethodsByAnnotations();
    }



    private void initializeMethodsByAnnotations() {
        methodCache = Arrays.stream(this.target.getClass().getDeclaredMethods())
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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args != null && args.length > 0 && args[0] instanceof RequestNotify requestNotify) {
            Annotation annotationRequestNotify = findAnnotation(requestNotify.getClass().getDeclaredAnnotations());

            if (methodCache.containsKey(annotationRequestNotify)) {
                Method methodToInvoke = methodCache.get(annotationRequestNotify);
                methodToInvoke.setAccessible(true);
                return methodToInvoke.invoke(target, args);
            }
        }

        return method.invoke(target, args);
    }



    private Annotation findAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> registeredAnnotations.contains(annotation.annotationType()))
                .findAny()
                .orElseThrow();
    }



    public static Notifiable createProxy(Notifiable target) {
        return (Notifiable) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class[]{Notifiable.class},
                new NotifyProxy(target)
        );
    }
}

