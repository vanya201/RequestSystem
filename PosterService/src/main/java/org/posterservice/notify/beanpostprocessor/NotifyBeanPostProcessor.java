package org.posterservice.notify.beanpostprocessor;

import org.posterservice.notify.Notifiable;
import org.posterservice.notify.annotation.Notify;
import org.posterservice.notify.proxy.NotifyProxy;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;


@Component
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
