package org.common.annotations;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EntityScan(basePackages = "org.common.model")
@ComponentScan(basePackages = {"org.common.converter", "org.common.config"})
public @interface ImportEntities {
}
