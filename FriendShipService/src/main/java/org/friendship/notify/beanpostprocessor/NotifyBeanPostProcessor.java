package org.friendship.notify.beanpostprocessor;

import io.micrometer.common.lang.NonNullApi;
import org.friendship.notify.Notifiable;
import org.friendship.notify.annotation.Notify;
import org.friendship.notify.proxy.NotifyProxy;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


@Component
@NonNullApi
public class NotifyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(Notify.class) && bean instanceof Notifiable)
            return NotifyProxy.createProxy((Notifiable) bean);
        return bean;
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return bean;
    }
}
