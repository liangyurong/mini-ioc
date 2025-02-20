package com.lyr.annotation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Schema(description = "自定义注解，用于标记需要被容器管理的类")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // 类
public @interface MiniComponent {
    String value() default ""; // 类名称
}
