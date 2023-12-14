package com.lyr.factory;

import com.lyr.bean.BeanDefinition;

// BeanFactory 接口定义了 IoC 容器的基本功能
public interface BeanFactory {

    // 根据名称获取 Bean
    Object getBean(String name);

    // 注册 Bean
    void registerBeanDefinition(String name, BeanDefinition beanDefinition);

}
