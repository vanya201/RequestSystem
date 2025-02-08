package org.common.annotations;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EntityScan(basePackages = "org.common.models")
public @interface ScanMainEntitys { }
