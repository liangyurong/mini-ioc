package com.lyr.innotation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Schema(description = "自定义注解")
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyBean {

}
