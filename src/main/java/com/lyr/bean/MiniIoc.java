package com.lyr.bean;

import com.lyr.factory.BeanFactory;
import java.util.HashMap;
import java.util.Map;

public class MiniIoc implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public Object getBean(String name) {
        // 根据名称获取 BeanDefinition
        BeanDefinition beanDefinition = beanDefinitionMap.get(name);
        if (beanDefinition == null) {
            throw new IllegalArgumentException("没有名称为 " + name + " 的bean被定义");
        }

        // 获取 Bean 实例
        Object bean = beanDefinition.getBean();
        if (bean == null) {
            // 创建 Bean 实例
            bean = createBean(beanDefinition);
            beanDefinition.setBean(bean);
        }
        return bean;
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        Object bean = createBean(beanDefinition);
        beanDefinition.setBean(bean);
        beanDefinitionMap.put(name, beanDefinition);
    }

    /**
     * 根据 BeanDefinition 创建 Bean 实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();
            return beanClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("创建bean错误", e);
        }
    }
}