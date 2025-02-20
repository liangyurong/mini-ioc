package com.lyr.bean;

import com.lyr.annotation.MiniComponent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Schema(description = "IOC容器的核心类,存储Bean的元数据，如类名、作用域")
@Slf4j
public class MiniApplicationContext {
    // 存储bean的容器
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    // 存储bean的类型信息
    private Map<String, Class<?>> beanClassMap = new ConcurrentHashMap<>();

    public MiniApplicationContext(String basePackage) {
        try {
            // 扫描指定包下的类
            Set<Class<?>> classes = this.scanPackage(basePackage);
            // 创建bean实例
            this.registerBeans(classes);
            // 注入依赖
            this.injectDependencies();
        } catch (Exception e) {
            throw new RuntimeException("初始化ioc容器失败: ", e);
        }
    }

    // 扫描指定包下的所有类
    private Set<Class<?>> scanPackage(String basePackage) throws Exception {
        Set<Class<?>> classes = new HashSet<>();
        String path = basePackage.replace('.', '/');
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        Enumeration<URL> resources = classLoader.getResources(path);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            scanDirectory(directory, basePackage, classes);
        }

        log.info("set信息："+classes);
        return classes;
    }

    // 递归扫描目录下的类文件
    private void scanDirectory(File directory, String basePackage, Set<Class<?>> classes) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    scanDirectory(file, basePackage + "." + file.getName(), classes);
                } else if (file.getName().endsWith(".class")) {
                    String className = basePackage + "." + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(MiniComponent.class)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }

    // 注册Bean
    private void registerBeans(Set<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            MiniComponent component = clazz.getAnnotation(MiniComponent.class);
            String beanName = component.value().isEmpty() ?
                    toLowerCase(clazz.getSimpleName()) : component.value();
            System.out.println("beanName:"+beanName);

            Object instance = clazz.getDeclaredConstructor().newInstance();
            beanMap.put(beanName, instance);
            beanClassMap.put(beanName, clazz);
        }
    }

    // 注入依赖
    private void injectDependencies() throws Exception {
        for (Map.Entry<String, Object> entry : beanMap.entrySet()) {
            Object bean = entry.getValue();
            Class<?> beanClass = beanClassMap.get(entry.getKey());

            for (Field field : beanClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    String beanName = toLowerCase(field.getType().getSimpleName());
                    Object dependencyBean = beanMap.get(beanName);
                    if (dependencyBean == null) {
                        throw new RuntimeException("No bean found for " + field.getType());
                    }
                    field.set(bean, dependencyBean);
                }
            }
        }
    }

    // 获取Bean
    public Object getBean(String name) {
        return beanMap.get(name);
    }

    // 获取Bean（带类型转换）
    public <T> T getBean(String name, Class<T> clazz) {
        Object bean = getBean(name);
        if (bean == null) {
            throw new RuntimeException("Bean '" + name + " 为空 ");
        }
        if (!clazz.isInstance(bean)) {
            throw new RuntimeException( name + "， 匹配不到对应的类: " + clazz.getName());
        }
        return clazz.cast(bean);
    }

    private String toLowerCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}
