package com.lyr.annotation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Schema(description = "自定义注解，用于标记需要依赖注入的字段")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD) // 字段
public @interface MiniAutowired {
}
