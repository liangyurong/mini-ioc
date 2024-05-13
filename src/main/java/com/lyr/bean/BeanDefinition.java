package com.lyr.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "保存 Bean 的定义信息，包括 Class 类型、属性、构造方法等")
@Data
public class BeanDefinition {

    private Object bean;
    private Class<?> beanClass;
    private String beanClassName;

    public BeanDefinition() {
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
