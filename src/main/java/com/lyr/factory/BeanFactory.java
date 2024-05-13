package com.lyr.factory;

import com.lyr.bean.BeanDefinition;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "定义IoC容器的基本功能")
public interface BeanFactory {

    /**
     * 根据名称获取 Bean
     * @param name
     * @return
     */
    Object getBeanByName(String name);

    /**
     * 注册 Bean
     * @param name
     * @param beanDefinition
     */
    void registerBean(String name, BeanDefinition beanDefinition);

}
